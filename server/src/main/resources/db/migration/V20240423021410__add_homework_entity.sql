CREATE TABLE t_homeworks
(
    homework_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    title       VARCHAR(255),
    start_time  TIMESTAMP WITHOUT TIME ZONE,
    end_time    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_t_homeworks PRIMARY KEY (homework_id)
);

CREATE TABLE t_homeworks_groups
(
    group_id    BIGINT NOT NULL,
    homework_id BIGINT NOT NULL
);

CREATE TABLE t_homeworks_problems
(
    homework_id BIGINT NOT NULL,
    problem_id  BIGINT NOT NULL
);

ALTER TABLE t_homeworks_groups
    ADD CONSTRAINT fk_thomgro_on_group FOREIGN KEY (group_id) REFERENCES t_groups (group_id);

ALTER TABLE t_homeworks_groups
    ADD CONSTRAINT fk_thomgro_on_homework FOREIGN KEY (homework_id) REFERENCES t_homeworks (homework_id);

ALTER TABLE t_homeworks_problems
    ADD CONSTRAINT fk_thompro_on_homework FOREIGN KEY (homework_id) REFERENCES t_homeworks (homework_id);

ALTER TABLE t_homeworks_problems
    ADD CONSTRAINT fk_thompro_on_problem FOREIGN KEY (problem_id) REFERENCES t_problems (problem_id);
