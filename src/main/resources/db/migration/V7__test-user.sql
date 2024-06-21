INSERT INTO usuarios (name, email, password,role)

VALUES
('root', 'root.user@forum.hub', '$2a$12$I35ViROD8r.NrSdUUgYe1uqD0AvmRfWgwiP2QM4j41j1E409buI3O', '0')

ON CONFLICT (email) DO NOTHING;