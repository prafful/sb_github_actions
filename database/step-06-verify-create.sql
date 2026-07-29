-- Step 06: Verify create-friend functionality
-- Run these in MySQL Workbench while the application is running.

-- 1. Check current friends before creating
SELECT id, name, age, city FROM friends ORDER BY id;

-- 2. After calling POST /friends/api/1.0/ with {"name":"Vikram","age":32,"city":"Bangalore"}
--    verify the new record
SELECT id, name, age, city FROM friends ORDER BY id;

-- Expected: a 5th row with id=5 (or next available AUTO_INCREMENT value)

-- 3. Verify the specific record
SELECT id, name, age, city FROM friends WHERE name = 'Vikram';

-- Expected: id=5, name=Vikram, age=32, city=Bangalore
