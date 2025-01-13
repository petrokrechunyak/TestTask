INSERT INTO lector (id, name, surname, degree, salary) VALUES
                                                           (1, 'Alice', 'Johnson', 'PROFESSOR', 1000),
                                                           (2, 'Bob', 'Smith', 'ASSOCIATE_PROFESSOR', 2000),
                                                           (3, 'Carol', 'Davis', 'ASSISTANT', 3000),
                                                           (4, 'David', 'Wilson', 'PROFESSOR', 4000),
                                                           (5, 'Eve', 'Thompson', 'ASSISTANT', 5000);

INSERT INTO department (id, name, head_id) VALUES
                                               (1, 'Computer Science', 1),
                                               (2, 'Mathematics', 2),
                                               (3, 'Physics', 4);

INSERT INTO department_lectors (department_id, lector_id) VALUES
                                                              (1, 1),
                                                              (1, 2),                                                              (1, 4),                                                              (2, 2),
                                                              (2, 5),
                                                              (3, 4);