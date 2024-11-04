CREATE TABLE "refresh_tokens"
(
  id         uuid                     DEFAULT gen_random_uuid() NOT NULL,
  identity   text                                               NOT NULL,
  token      text                                               NOT NULL,
  created_at timestamp with time zone DEFAULT now()             NOT NULL
);