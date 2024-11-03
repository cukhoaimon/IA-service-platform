CREATE TABLE "refresh_tokens"
(
  user_id    uuid                     DEFAULT gen_random_uuid() NOT NULL,
  token      text                                               NOT NULL,
  created_at timestamp with time zone DEFAULT now()             NOT NULL
);