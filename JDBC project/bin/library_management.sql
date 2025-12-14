-- =======================================
-- Library Management System - Oracle SQL
-- =======================================

-- Drop tables if they already exist (safe re-run)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE issues';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE members';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE books';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- ===============================
-- BOOKS TABLE
-- ===============================
CREATE TABLE books (
    book_id NUMBER PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    author VARCHAR2(50) NOT NULL,
    quantity NUMBER CHECK (quantity >= 0)
);

-- ===============================
-- MEMBERS TABLE
-- ===============================
CREATE TABLE members (
    member_id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    contact VARCHAR2(50)
);

-- ===============================
-- ISSUES TABLE
-- ===============================
CREATE TABLE issues (
    issue_id NUMBER PRIMARY KEY,
    book_id NUMBER REFERENCES books(book_id),
    member_id NUMBER REFERENCES members(member_id),
    issue_date DATE DEFAULT SYSDATE,
    return_date DATE
);

-- ===============================
-- SAMPLE DATA (BOOKS)
-- ===============================
INSERT INTO books VALUES (1, 'Java Programming', 'Rathindra', 5);
INSERT INTO books VALUES (2, 'Data Structures', 'Sommerville', 3);
INSERT INTO books VALUES (3, 'Algorithms', 'Cormen', 4);

-- ===============================
-- SAMPLE DATA (MEMBERS)
-- ===============================
INSERT INTO members VALUES (1, 'Alice', 'alice@example.com');
INSERT INTO members VALUES (2, 'Bob', 'bob@example.com');
INSERT INTO members VALUES (3, 'Charlie', 'charlie@example.com');

-- ===============================
-- SAMPLE DATA (ISSUES)
-- ===============================
INSERT INTO issues VALUES (1, 1, 1, SYSDATE, NULL);
INSERT INTO issues VALUES (2, 2, 2, SYSDATE, NULL);

-- Save changes
COMMIT;

-- ===============================
-- VERIFY DATA
-- ===============================
SELECT * FROM books;
SELECT * FROM members;
SELECT * FROM issues;
