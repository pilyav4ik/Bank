create table department
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table employee
(
    id bigint auto_increment primary key,
    name varchar(255) null,
    department_id bigint null,
    constraint FK_department
        foreign key (department_id) references department (id)
);

INSERT INTO department (id, name)
VALUES (1, 'Development'),
       (2, 'Testing'),
       (3, 'Marketing');

INSERT INTO employee (id, name, department_id)
VALUES (1, 'Mike Dev', 1),
       (2, 'Tom Dev', 1),
       (3, 'Rob Tester', 2),
       (4, 'Emma Market', 3);