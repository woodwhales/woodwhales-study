DELETE FROM user;

INSERT INTO user (id, user_name, password, roles) VALUES
(1, 'admin', 'c41d7c66e1b8404545aa3a0ece2006ac', 'user:update,user:add'),
(2, 'tom', '660d9c5997ff08ba6d1961d9ddd8ee73', 'user:add');
