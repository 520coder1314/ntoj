CREATE TABLE t_groups
(
    group_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(255),
    CONSTRAINT pk_t_groups PRIMARY KEY (group_id)
);

CREATE TABLE t_groups_users
(
    t_groups_group_id BIGINT NOT NULL,
    users_user_id     BIGINT NOT NULL
);

ALTER TABLE t_groups_users
    ADD CONSTRAINT fk_tgrouse_on_group FOREIGN KEY (t_groups_group_id) REFERENCES t_groups (group_id);

ALTER TABLE t_groups_users
    ADD CONSTRAINT fk_tgrouse_on_user FOREIGN KEY (users_user_id) REFERENCES t_users (user_id);