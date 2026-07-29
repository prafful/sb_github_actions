-- Step 02 inserts the same four friends currently held in the Java ArrayList.
-- Explicit IDs make the sample data predictable for future lessons.
-- INSERT IGNORE makes this script safe to run repeatedly.

INSERT IGNORE INTO friends (id, name, age, city)
VALUES
    (1, 'Rajini', 70, 'Chennai'),
    (2, 'Bhargavi', 30, 'Hyderabad'),
    (3, 'Dhanush', 35, 'Chennai'),
    (4, 'Priyanshu', 25, 'Delhi');
