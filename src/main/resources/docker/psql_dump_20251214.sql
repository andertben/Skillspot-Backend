--
-- PostgreSQL database dump
--

\restrict Wgk7GBwR8L9xneRQ4B6d7HRt0Tz5gdEHTya6AlyaIAwVLCQgLLgAAMZIPcOXAv3

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg110+1)
-- Dumped by pg_dump version 18.1 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: skillspot; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA skillspot;


ALTER SCHEMA skillspot OWNER TO pg_database_owner;

--
-- Name: SCHEMA skillspot; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA skillspot IS 'standard skillspot schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: anbieter; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.anbieter (
    anbieter_id integer NOT NULL,
    beschreibung text,
    benutzer_id integer,
    firmen_name character varying(255),
    location_lat numeric(10,6),
    location_lon numeric(10,6)
);


ALTER TABLE skillspot.anbieter OWNER TO postgres;

--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.anbieter_anbieter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.anbieter_anbieter_id_seq OWNER TO postgres;

--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.anbieter_anbieter_id_seq OWNED BY skillspot.anbieter.anbieter_id;


--
-- Name: benutzer; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.benutzer (
    benutzer_id integer NOT NULL,
    beschreibung text,
    email text,
    password_hash text,
    vorname character varying(255),
    nachname character varying(255)
);


ALTER TABLE skillspot.benutzer OWNER TO postgres;

--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.benutzer_benutzer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.benutzer_benutzer_id_seq OWNER TO postgres;

--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.benutzer_benutzer_id_seq OWNED BY skillspot.benutzer.benutzer_id;


--
-- Name: bewertung; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.bewertung (
    bewertung_id integer NOT NULL,
    dienstleistung_id integer NOT NULL,
    benutzer_id integer NOT NULL,
    text text,
    buchung_id integer,
    anbieter_id integer,
    bewertung integer,
    erstellungsdatum timestamp without time zone DEFAULT now(),
    CONSTRAINT bewertung_bewertung_check CHECK (((bewertung >= 1) AND (bewertung <= 5)))
);


ALTER TABLE skillspot.bewertung OWNER TO postgres;

--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.bewertung_bewertung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.bewertung_bewertung_id_seq OWNER TO postgres;

--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.bewertung_bewertung_id_seq OWNED BY skillspot.bewertung.bewertung_id;


--
-- Name: buchung; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.buchung (
    buchung_id integer NOT NULL,
    dienstleistung_id integer NOT NULL,
    benutzer_id integer NOT NULL,
    text text,
    anfragedatum timestamp without time zone,
    status text,
    preis numeric(10,2)
);


ALTER TABLE skillspot.buchung OWNER TO postgres;

--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.buchung_buchung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.buchung_buchung_id_seq OWNER TO postgres;

--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.buchung_buchung_id_seq OWNED BY skillspot.buchung.buchung_id;


--
-- Name: dienstleistung; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.dienstleistung (
    dienstleistung_id integer NOT NULL,
    anbieter_id integer NOT NULL,
    kategorie_id integer NOT NULL,
    beschreibung text,
    title text,
    preis numeric(10,2)
);


ALTER TABLE skillspot.dienstleistung OWNER TO postgres;

--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.dienstleistung_dienstleistung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.dienstleistung_dienstleistung_id_seq OWNER TO postgres;

--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.dienstleistung_dienstleistung_id_seq OWNED BY skillspot.dienstleistung.dienstleistung_id;


--
-- Name: kategorie; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.kategorie (
    kategorie_id integer NOT NULL,
    bezeichnung text,
    oberkategorie_id integer,
    icon text
);


ALTER TABLE skillspot.kategorie OWNER TO postgres;

--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.kategorie_kategorie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.kategorie_kategorie_id_seq OWNER TO postgres;

--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.kategorie_kategorie_id_seq OWNED BY skillspot.kategorie.kategorie_id;


--
-- Name: anbieter anbieter_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.anbieter ALTER COLUMN anbieter_id SET DEFAULT nextval('skillspot.anbieter_anbieter_id_seq'::regclass);


--
-- Name: benutzer benutzer_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.benutzer ALTER COLUMN benutzer_id SET DEFAULT nextval('skillspot.benutzer_benutzer_id_seq'::regclass);


--
-- Name: bewertung bewertung_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung ALTER COLUMN bewertung_id SET DEFAULT nextval('skillspot.bewertung_bewertung_id_seq'::regclass);


--
-- Name: buchung buchung_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.buchung ALTER COLUMN buchung_id SET DEFAULT nextval('skillspot.buchung_buchung_id_seq'::regclass);


--
-- Name: dienstleistung dienstleistung_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung ALTER COLUMN dienstleistung_id SET DEFAULT nextval('skillspot.dienstleistung_dienstleistung_id_seq'::regclass);


--
-- Name: kategorie kategorie_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie ALTER COLUMN kategorie_id SET DEFAULT nextval('skillspot.kategorie_kategorie_id_seq'::regclass);


--
-- Data for Name: anbieter; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.anbieter (anbieter_id, beschreibung, benutzer_id, firmen_name, location_lat, location_lon) FROM stdin;
1	Guten Tag ich bin der Herr Müller, ich biete Eletro Dienstleistungenan 	1	Elektro Müller	\N	\N
\.


--
-- Data for Name: benutzer; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.benutzer (benutzer_id, beschreibung, email, password_hash, vorname, nachname) FROM stdin;
1	Elektromeister	petermüller@mail.de	\N	Peter	Müller
\.


--
-- Data for Name: bewertung; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.bewertung (bewertung_id, dienstleistung_id, benutzer_id, text, buchung_id, anbieter_id, bewertung, erstellungsdatum) FROM stdin;
1	1	1	Sehr guter Service	1	1	5	2025-12-14 13:56:13.747071
\.


--
-- Data for Name: buchung; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.buchung (buchung_id, dienstleistung_id, benutzer_id, text, anfragedatum, status, preis) FROM stdin;
1	1	1	Erneuerung der Elekroinstallation 	2025-12-14 00:00:00	Ausgeführt	5000.00
\.


--
-- Data for Name: dienstleistung; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.dienstleistung (dienstleistung_id, anbieter_id, kategorie_id, beschreibung, title, preis) FROM stdin;
1	1	1	Ich wechsel dir fachgerecht deine Elektroinstallatione 	generelle Elektroinstallation	5000.00
\.


--
-- Data for Name: kategorie; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id, icon) FROM stdin;
1	Elektro	\N	\N
2	Steckdose anschließen	1	\N
\.


--
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
\.


--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.anbieter_anbieter_id_seq', 1, true);


--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.benutzer_benutzer_id_seq', 1, true);


--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.bewertung_bewertung_id_seq', 1, true);


--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.buchung_buchung_id_seq', 1, true);


--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.dienstleistung_dienstleistung_id_seq', 1, true);


--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.kategorie_kategorie_id_seq', 2, true);


--
-- Name: anbieter anbieter_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.anbieter
    ADD CONSTRAINT anbieter_pkey PRIMARY KEY (anbieter_id);


--
-- Name: benutzer benutzer_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.benutzer
    ADD CONSTRAINT benutzer_pkey PRIMARY KEY (benutzer_id);


--
-- Name: bewertung bewertung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT bewertung_pkey PRIMARY KEY (bewertung_id);


--
-- Name: buchung buchung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT buchung_pkey PRIMARY KEY (buchung_id);


--
-- Name: dienstleistung dienstleistung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT dienstleistung_pkey PRIMARY KEY (dienstleistung_id);


--
-- Name: kategorie kategorie_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT kategorie_pkey PRIMARY KEY (kategorie_id);


--
-- Name: anbieter fk_anbieter_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.anbieter
    ADD CONSTRAINT fk_anbieter_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: bewertung fk_bewertung_anbieter; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_anbieter FOREIGN KEY (anbieter_id) REFERENCES skillspot.anbieter(anbieter_id);


--
-- Name: bewertung fk_bewertung_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: bewertung fk_bewertung_buchung; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_buchung FOREIGN KEY (buchung_id) REFERENCES skillspot.buchung(buchung_id);


--
-- Name: bewertung fk_bewertung_dl; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_dl FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


--
-- Name: buchung fk_buchung_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT fk_buchung_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: buchung fk_buchung_dl; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT fk_buchung_dl FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


--
-- Name: dienstleistung fk_dl_anbieter; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT fk_dl_anbieter FOREIGN KEY (anbieter_id) REFERENCES skillspot.anbieter(anbieter_id);


--
-- Name: dienstleistung fk_dl_kategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT fk_dl_kategorie FOREIGN KEY (kategorie_id) REFERENCES skillspot.kategorie(kategorie_id);


--
-- Name: kategorie fk_kategorie_oberkategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT fk_kategorie_oberkategorie FOREIGN KEY (oberkategorie_id) REFERENCES skillspot.kategorie(kategorie_id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

\unrestrict Wgk7GBwR8L9xneRQ4B6d7HRt0Tz5gdEHTya6AlyaIAwVLCQgLLgAAMZIPcOXAv3

