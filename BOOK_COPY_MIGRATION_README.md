# Book Copy Model Migration

## Problem Solved

The old model had a significant issue: books had a `quantity` field that showed how many copies existed, but when someone borrowed a book, there was no way to know which specific copy was borrowed. This created problems when returning books.

## Solution

A new `BookCopy` model was created that represents each physical copy of a book:

### New Entities
- **BookCopy**: Each physical copy of a book
- **Book**: No longer has a `quantity` field, but has a relationship with `BookCopy`
- **Borrow**: Now connects to `BookCopy` instead of `Book`

### Key Changes

1. **Book Model**:
   - Removed the `quantity` field
   - Added `OneToMany` relationship with `BookCopy`
   - Added calculated methods: `getTotalCopies()`, `getAvailableCopies()`

2. **BookCopy Model**:
   - `copyId`: Unique ID for each copy
   - `book`: Relationship with the book
   - `copyNumber`: Copy number (1, 2, 3, etc.)
   - `isAvailable`: Whether it's available for borrowing
   - `condition`: Condition of the copy

3. **Borrow Model**:
   - Now connects to `BookCopy` instead of `Book`
   - The `BorrowId` uses `bookCopyId` instead of `bookId`

## New API Endpoints

### BookCopy Management
- `GET /api/book-copies/book/{bookId}` - All copies of a book
- `GET /api/book-copies/book/{bookId}/available` - Available copies
- `GET /api/book-copies/book/{bookId}/available/count` - Number of available copies
- `POST /api/book-copies/book/{bookId}/add/{quantity}` - Add copies
- `PUT /api/book-copies/{copyId}` - Update copy
- `DELETE /api/book-copies/{copyId}` - Delete copy

### Migration Endpoints
- `POST /api/migration/create-default-copies` - Create default copies
- `POST /api/migration/migrate-quantities` - Migration from old model

## How to Migrate

### Step 1: Database Schema Update
You need to create the `BOOK_COPIES` table:

```sql
CREATE TABLE BOOK_COPIES (
    COPY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    BOOK_ID BIGINT NOT NULL,
    COPY_NUMBER INTEGER NOT NULL,
    IS_AVAILABLE BOOLEAN NOT NULL DEFAULT TRUE,
    CONDITION VARCHAR(50),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(BOOK_ID)
);
```

### Step 2: Update BORROWS table
```sql
-- Add new column
ALTER TABLE BORROWS ADD COLUMN BOOK_COPY_ID BIGINT;

-- You'll need to migrate existing borrow data manually
-- This is complex and depends on your existing data
```

### Step 3: Run Migration
Call the endpoint:
```
POST /api/migration/create-default-copies
```

This will create a default copy for each book that doesn't have copies.

### Step 4: Clean up (Optional)
After migration, you can remove the `QUANTITY` column from the `BOOKS` table:

```sql
ALTER TABLE BOOKS DROP COLUMN QUANTITY;
```

## Advantages of the New Model

1. **Precise tracking**: Each copy has its own ID
2. **Better management**: You can track the condition of each copy
3. **Return problem solution**: You know exactly which copy is being returned
4. **Extensibility**: You can add more information for each copy

## Important Notes

- The API for borrow/return remains the same (uses `bookId`)
- The logic automatically finds an available copy for borrowing
- For returns, it finds the correct copy that the user borrowed
- The BookDTO still has a `quantity` field that is calculated from the copies