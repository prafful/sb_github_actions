-- Step 02 inserts the same four friends currently held in the Java ArrayList.
-- Explicit IDs make the sample data predictable for future lessons.
-- INSERT IGNORE makes this script safe to run repeatedly.

INSERT IGNORE INTO friends (id, name, age, city)
VALUES
    (1, 'OBB', 70, 'Chennai'),
    (2, 'OMG', 30, 'Hyderabad'),
    (3, 'OTR', 35, 'Chennai'),
    (4, 'CBJ', 25, 'Delhi'),
    (5, 'TTO', 25, 'JhoomriTalaiya');