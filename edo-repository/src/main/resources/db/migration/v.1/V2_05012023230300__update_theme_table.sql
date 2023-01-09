alter table if exists theme
    rename column parent_them to parent_theme;

COMMENT  ON COLUMN theme.parent_theme is 'родительская тема';