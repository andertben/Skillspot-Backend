CREATE TABLE IF NOT EXISTS skillspot.kategorie_i18n (
    kategorie_id integer NOT NULL,
    lang VARCHAR(2) NOT NULL,
    bezeichnung TEXT NOT NULL,
    PRIMARY KEY (kategorie_id, lang),
    CONSTRAINT fk_kategorie_i18n_kategorie
      FOREIGN KEY (kategorie_id)
      REFERENCES skillspot.kategorie(kategorie_id)
      ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_kategorie_i18n_lang ON skillspot.kategorie_i18n(lang);
