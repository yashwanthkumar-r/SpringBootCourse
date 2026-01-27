INSERT INTO patient (name, gender, birth_date, email, blood_group)
VALUES
    ('Aarav Sharma', 'MALE', '1990-05-10', 'aarav.sharma@example.com', 'O_POSITIVE'),
    ('Diya Patel', 'FEMALE', '1995-08-20', 'diya.patel@example.com', 'A_POSITIVE'),
    ('Dishant Verma', 'MALE', '1988-03-15', 'dishant.verma@example.com', 'A_POSITIVE'),
    ('Neha Iyer', 'FEMALE', '1992-12-01', 'neha.iyer@example.com', 'AB_POSITIVE'),
    ('Kabir Singh', 'MALE', '1993-07-11', 'kabir.singh@example.com', 'O_POSITIVE');


INSERT INTO doctor (name, specialization, email) VALUES
('Dr. Ananya Sharma', 'Cardiology', 'ananya.sharma@hospital.com'),
('Dr. Rahul Verma', 'Neurology', 'rahul.verma@hospital.com'),
('Dr. Priya Nair', 'Dermatology', 'priya.nair@hospital.com'),
('Dr. Arjun Reddy', 'Orthopedics', 'arjun.reddy@hospital.com'),
('Dr. Sneha Iyer', 'Pediatrics', 'sneha.iyer@hospital.com');


Insert INTO Department (name, head_doctor_id) values
('Cardiology',1),
('General',3);