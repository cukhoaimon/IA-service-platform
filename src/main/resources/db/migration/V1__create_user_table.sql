CREATE TABLE "user"
(
  id         UUID                              DEFAULT gen_random_uuid(),
  email      TEXT                     NOT NULL UNIQUE,
  password   TEXT                     NOT NULL,
  created_at timestamp WITH TIME ZONE NOT NULL DEFAULT (now()),

  primary key (id, email)
);