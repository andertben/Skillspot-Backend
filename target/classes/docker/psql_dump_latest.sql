--
-- PostgreSQL database dump
--

\restrict kXjcQvKqodSdsPR4IcmG8dDSqGy0SdBoNHTOnQIhZfGv5iS5bOqqUojbeUQajtr

-- Dumped from database version 17.7 (Debian 17.7-3.pgdg13+1)
-- Dumped by pg_dump version 17.7 (Debian 17.7-3.pgdg13+1)

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
-- Name: skillspot; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA skillspot;


--
-- Name: SCHEMA skillspot; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA skillspot IS 'standard skillspot schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: anbieter; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.anbieter (
    anbieter_id integer NOT NULL,
    beschreibung text,
    benutzer_id integer,
    firmen_name character varying(255),
    location_lat numeric(10,6),
    location_lon numeric(10,6)
);


--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.anbieter_anbieter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.anbieter_anbieter_id_seq OWNED BY skillspot.anbieter.anbieter_id;


--
-- Name: benutzer; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.benutzer (
    benutzer_id integer NOT NULL,
    beschreibung text,
    email text,
    password_hash text,
    vorname character varying(255),
    nachname character varying(255),
    auth0_sub text,
    test_col text,
    rolle character varying(50),
    display_name character varying(255),
    location_lat numeric(10,6),
    location_lon numeric(10,6),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    address character varying(255)
);


--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.benutzer_benutzer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.benutzer_benutzer_id_seq OWNED BY skillspot.benutzer.benutzer_id;


--
-- Name: bewertung; Type: TABLE; Schema: skillspot; Owner: -
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


--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.bewertung_bewertung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.bewertung_bewertung_id_seq OWNED BY skillspot.bewertung.bewertung_id;


--
-- Name: buchung; Type: TABLE; Schema: skillspot; Owner: -
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


--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.buchung_buchung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.buchung_buchung_id_seq OWNED BY skillspot.buchung.buchung_id;


--
-- Name: chat_message; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.chat_message (
    message_id bigint NOT NULL,
    thread_id bigint NOT NULL,
    sender_sub text NOT NULL,
    text text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    is_read boolean DEFAULT false NOT NULL
);


--
-- Name: chat_message_message_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.chat_message_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: chat_message_message_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.chat_message_message_id_seq OWNED BY skillspot.chat_message.message_id;


--
-- Name: chat_thread; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.chat_thread (
    thread_id bigint NOT NULL,
    dienstleistung_id bigint NOT NULL,
    user_sub text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: chat_thread_thread_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

ALTER TABLE skillspot.chat_thread ALTER COLUMN thread_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME skillspot.chat_thread_thread_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: dienstleistung; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.dienstleistung (
    dienstleistung_id integer NOT NULL,
    anbieter_id integer NOT NULL,
    kategorie_id integer NOT NULL,
    beschreibung text,
    title text,
    preis numeric(10,2)
);


--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.dienstleistung_dienstleistung_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.dienstleistung_dienstleistung_id_seq OWNED BY skillspot.dienstleistung.dienstleistung_id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200),
    type character varying(20),
    script character varying(1000),
    checksum integer,
    installed_by character varying(100),
    installed_on timestamp without time zone DEFAULT now(),
    execution_time integer,
    success boolean
);


--
-- Name: kategorie; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.kategorie (
    kategorie_id integer NOT NULL,
    bezeichnung text,
    oberkategorie_id integer,
    icon text
);


--
-- Name: kategorie_i18n; Type: TABLE; Schema: skillspot; Owner: -
--

CREATE TABLE skillspot.kategorie_i18n (
    kategorie_i18n_id bigint NOT NULL,
    kategorie_id bigint NOT NULL,
    lang character varying(5) NOT NULL,
    bezeichnung text NOT NULL
);


--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.kategorie_i18n_kategorie_i18n_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.kategorie_i18n_kategorie_i18n_id_seq OWNED BY skillspot.kategorie_i18n.kategorie_i18n_id;


--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: -
--

CREATE SEQUENCE skillspot.kategorie_kategorie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: -
--

ALTER SEQUENCE skillspot.kategorie_kategorie_id_seq OWNED BY skillspot.kategorie.kategorie_id;


--
-- Name: anbieter anbieter_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.anbieter ALTER COLUMN anbieter_id SET DEFAULT nextval('skillspot.anbieter_anbieter_id_seq'::regclass);


--
-- Name: benutzer benutzer_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.benutzer ALTER COLUMN benutzer_id SET DEFAULT nextval('skillspot.benutzer_benutzer_id_seq'::regclass);


--
-- Name: bewertung bewertung_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung ALTER COLUMN bewertung_id SET DEFAULT nextval('skillspot.bewertung_bewertung_id_seq'::regclass);


--
-- Name: buchung buchung_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.buchung ALTER COLUMN buchung_id SET DEFAULT nextval('skillspot.buchung_buchung_id_seq'::regclass);


--
-- Name: chat_message message_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.chat_message ALTER COLUMN message_id SET DEFAULT nextval('skillspot.chat_message_message_id_seq'::regclass);


--
-- Name: dienstleistung dienstleistung_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.dienstleistung ALTER COLUMN dienstleistung_id SET DEFAULT nextval('skillspot.dienstleistung_dienstleistung_id_seq'::regclass);


--
-- Name: kategorie kategorie_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie ALTER COLUMN kategorie_id SET DEFAULT nextval('skillspot.kategorie_kategorie_id_seq'::regclass);


--
-- Name: kategorie_i18n kategorie_i18n_id; Type: DEFAULT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie_i18n ALTER COLUMN kategorie_i18n_id SET DEFAULT nextval('skillspot.kategorie_i18n_kategorie_i18n_id_seq'::regclass);


--
-- Data for Name: anbieter; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.anbieter (anbieter_id, beschreibung, benutzer_id, firmen_name, location_lat, location_lon) FROM stdin;
1	Guten Tag ich bin der Herr Müller, ich biete Eletro Dienstleistungenan 	1	Elektro Müller	52.411500	12.550600
2	Zuverlässiger haushaltsservice für Haushalte und kleine Betriebe 	2	Elektro Service Havel	52.396800	12.513200
3	Professionelle Haushaltshilfe für Reinigung und Alltagstätigkeiten  	3	Sauber & Klar Haushaltshilfe 	52.409200	12.560100
4	Unterstützung im Haushalt - flexibel und zuverlässig	4	Haushaltshilfe Havelblick	52.412900	12.553900
5	Gartenpflege, Rasenmähen und saisonale Arbeiten 	5	Gartenbau Brandenburg	52.378400	12.486700
6	Pflege von Grünanlagen und privaten Gärten	6	Grünpflege an der Havel	52.423700	12.543100
7	IT-Hilfe für Privatkunden: WLAN, PC und Drucker 	7	IT-Hilfe Brandenburg	52.410100	12.556400
8	Technische Unterstützung rund um Smart-Home und Multimedia	8	Technik Service Dominsel	52.406600	12.540200
9	\N	58	Peter Lustig GmbH	52.371581	12.932913
10	\N	62	Markus Müller	52.410511	12.690256
11	\N	63	Gärtnerei Peter GmbH	52.442937	12.528437
12	\N	15	Testnutzer	52.460930	12.544749
13	\N	65	Schopf GmbH	52.397701	12.537345
\.


--
-- Data for Name: benutzer; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.benutzer (benutzer_id, beschreibung, email, password_hash, vorname, nachname, auth0_sub, test_col, rolle, display_name, location_lat, location_lon, created_at, updated_at, address) FROM stdin;
1	Elektromeister	petermüller@mail.de	\N	Peter	Müller	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
2	Elektrotechniker 	thomas.schneider@mail.de	\N	Thomas	Schneider	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
3	Haushaltshilfe	sabine.krueger@mail.de	\N	Sabine 	Krueger 	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
4	Haushaltshilfe	anja.lehmann@mail.de	\N	Anja	Lehmann 	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
5	Garten- und Landschaftsbauer 	markus.hoffmann@mail.de	\N	Markus 	Hoffmann	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
6	Grünpfleger 	jens.becker@mail.de	\N	Jens 	Becker 	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
7	IT-Dienstleister 	daniel.weber@mail.de	\N	Daniel 	Weber	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
8	Technischer Service 	stefan.richter@mail.de	\N	Stefan 	Richter	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
9	Kunde	max.mustermann@mail.de	\N	Max	Mustermann	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
10	Kundin	laura.schulz@mail.de	\N	Laura	Schulz	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
11	Kunde	tim.wagner@mail.de	\N	Tim	Wagner	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
12	Kundin	sarah.koch@mail.de	\N	Sarah	Koch	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
13	Kunde	jonas.braun@mail.de	\N	Jonas 	Braun	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
14	Kundin	lena.neumann@mail.de	\N	Lena 	Neumann	\N	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
62	\N	\N	\N	\N	\N	auth0|6974a49af690ad94a2eea2a1	\N	PROVIDER	Markus Müller	52.410511	12.690256	2026-01-24 11:55:03.582954	2026-01-24 11:55:03.582954	\N
63	\N	\N	\N	\N	\N	auth0|6974a5af1f7b77061b874e16	\N	PROVIDER	Gärtnerei Peter GmbH	52.442937	12.528437	2026-01-24 12:07:20.102859	2026-01-24 12:07:20.102859	\N
15	\N	\N	\N	\N	\N	auth0|69661e55f559c5cbaba4fede	\N	PROVIDER	Testnutzer	52.460930	12.544749	2026-01-23 00:06:11.327142	2026-01-24 14:00:58.690259	Hauptstraße 3, 14778 Beetzsee
65	\N	\N	\N	\N	\N	auth0|6975172a873ec3263f2b8aaa	\N	PROVIDER	Schopf GmbH	52.397701	12.537345	2026-01-24 20:03:12.723391	2026-01-24 20:03:12.723391	\N
57	\N	\N	\N	\N	\N	auth0|69736afeb0529772d1b941e7	\N	USER	Testnutzer2	\N	\N	2026-01-23 13:35:22.281436	2026-01-23 13:35:22.281436	\N
58	\N	\N	\N	\N	\N	auth0|697409dcddf44b588fdbb1e0	\N	PROVIDER	Peter Lustig GmbH	52.371581	12.932913	2026-01-24 00:53:31.690771	2026-01-24 11:52:15.225966	\N
\.


--
-- Data for Name: bewertung; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.bewertung (bewertung_id, dienstleistung_id, benutzer_id, text, buchung_id, anbieter_id, bewertung, erstellungsdatum) FROM stdin;
1	1	1	Sehr guter Service	1	1	5	2025-12-14 13:56:13.747071
2	1	9	Sehr gute arbeit, alles top.	2	1	5	2025-12-18 13:56:13.747
3	2	10	Lampe perfekt montiert.	3	1	5	2025-12-19 20:56:13.747
4	4	13	Wohnung sehr gründlich.	5	3	5	2025-12-19 13:56:13.747
5	6	10	Rasen sauber gemäht.	7	5	5	2025-12-17 13:56:13.747
6	8	13	WLAN läuft stabil.	9	7	5	2025-12-16 13:56:13.747
7	2	63	Gute Arbeit, Steckdose gut angeschlossen. 	\N	1	4	2026-01-24 15:52:33.161095
8	2	63	Bisschen dreckig nach der Montage, sonst ganz gut	\N	1	3	2026-01-24 15:58:46.401792
9	2	63	Top Arbeit	\N	1	5	2026-01-24 15:59:02.254051
\.


--
-- Data for Name: buchung; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.buchung (buchung_id, dienstleistung_id, benutzer_id, text, anfragedatum, status, preis) FROM stdin;
1	1	1	Erneuerung der Elekroinstallation 	2025-12-14 00:00:00	Ausgeführt	5000.00
2	1	9	Steckdose in der Küche anschließen 	2025-12-18 00:00:00	Ausgeführt 	85.00
3	2	10	Deckenlampe montieren 	2025-12-20 00:00:00	Ausgeführt 	60.00
4	3	11	Herd anschließen 	2025-12-22 00:00:00	In Bearbeitung 	120.00
5	4	13	Wohnung reinigen 	2025-12-19 00:00:00	Ausgeführt 	110.00
6	5	14	Fenster putzen	2025-12-21 00:00:00	Ausgeführt 	75.00
7	6	10	Rasen mähen	2025-12-17 00:00:00	Ausgeführt 	90.00
8	7	11	Hecke schneiden 	2025-12-26 00:00:00	Ausgeführt	130.00
9	8	13	WLAN einrichten 	2025-12-16 00:00:00	Ausgeführt 	65.00
10	9	14	Drucker einrichten 	2025-12-27 00:00:00	Ausgeführt	45.00
\.


--
-- Data for Name: chat_message; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.chat_message (message_id, thread_id, sender_sub, text, created_at, is_read) FROM stdin;
1	1	auth0|69736afeb0529772d1b941e7	hallo	2026-01-23 15:21:09.032618	f
2	1	auth0|69736afeb0529772d1b941e7	Ich würde das buchen	2026-01-23 15:26:26.853837	f
3	1	auth0|69736afeb0529772d1b941e7	test	2026-01-23 15:26:35.579114	f
4	1	auth0|69736afeb0529772d1b941e7	hallo	2026-01-23 15:28:41.530598	f
5	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:03.70967	f
6	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:04.426342	f
7	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:04.788889	f
8	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:05.10314	f
9	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:05.396305	f
10	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:05.712209	f
11	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:06.143514	f
12	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:06.427184	f
13	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:06.730814	f
14	1	auth0|69736afeb0529772d1b941e7	h	2026-01-23 15:33:07.040581	f
15	6	auth0|69661e55f559c5cbaba4fede	Kannst du mir Beete anlegen?	2026-01-24 19:42:08.345553	t
16	6	auth0|6974a5af1f7b77061b874e16	Klar kein Problem	2026-01-24 19:42:46.852581	t
17	6	auth0|6974a5af1f7b77061b874e16	Wann denn?	2026-01-24 19:59:50.687237	t
18	6	auth0|69661e55f559c5cbaba4fede	Morgen	2026-01-24 20:00:12.485366	t
19	9	auth0|69736afeb0529772d1b941e7	Hallo	2026-01-24 20:04:58.665678	t
20	9	auth0|6975172a873ec3263f2b8aaa	klar	2026-01-24 20:09:46.030983	f
21	10	auth0|6975172a873ec3263f2b8aaa	test	2026-01-24 21:32:17.204173	f
\.


--
-- Data for Name: chat_thread; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.chat_thread (thread_id, dienstleistung_id, user_sub, created_at, updated_at) FROM stdin;
1	2	auth0|69736afeb0529772d1b941e7	2026-01-23 14:53:27.561709	2026-01-23 14:53:27.56176
2	3	auth0|69736afeb0529772d1b941e7	2026-01-23 15:39:14.1586	2026-01-23 15:39:14.158638
3	2	auth0|69661e55f559c5cbaba4fede	2026-01-23 15:52:52.119596	2026-01-23 15:52:52.120017
4	2	test-user-sub	2026-01-23 16:05:40.176201	2026-01-23 16:05:40.176233
5	2	auth0|6974a5af1f7b77061b874e16	2026-01-24 16:33:35.294298	2026-01-24 16:33:35.294397
6	13	auth0|69661e55f559c5cbaba4fede	2026-01-24 19:41:57.535321	2026-01-24 19:41:57.535383
7	13	auth0|69736afeb0529772d1b941e7	2026-01-24 20:01:24.59775	2026-01-24 20:01:24.597795
8	14	auth0|6975172a873ec3263f2b8aaa	2026-01-24 20:04:23.919631	2026-01-24 20:04:23.919879
9	14	auth0|69736afeb0529772d1b941e7	2026-01-24 20:04:46.497463	2026-01-24 20:04:46.497508
10	2	auth0|6975172a873ec3263f2b8aaa	2026-01-24 21:22:04.102094	2026-01-24 21:22:04.110543
\.


--
-- Data for Name: dienstleistung; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.dienstleistung (dienstleistung_id, anbieter_id, kategorie_id, beschreibung, title, preis) FROM stdin;
1	1	1	Ich wechsel dir fachgerecht deine Elektroinstallatione 	generelle Elektroinstallation	5000.00
5	3	14	Wohnung reinigen	Reinigung einer 2-Zimmer-Wohnung 	110.00
6	4	15	Fenster putzen	Fensterreinigung innen & außen 	75.00
7	5	26	Rasen mähen 	Rasen mähen inkl. Aufräumen 	90.00
8	6	27	Hecke scheiden 	Heckenschnitt inkl. Aufräumen 	130.00
9	7	36	WLAN einrichten 	Router & Repeater einrichten 	65.00
10	8	37	Drucker einrichten 	WLAn-Drucker inkl. Scan 	45.00
2	1	2	Steckdose anschließen 	Anschluss einer einzelnen Steckdose 	85.00
3	1	3	Lampe montieren 	Montage einer decken- oder Wandlampe 	60.00
4	2	5	Herd anschließen 	fachgerechter Herdanschluss ink. Prüfung 	120.00
13	11	30	\N	Wir legen dir Beete an	\N
14	13	31	\N	Wir schneiden ihre Hecke	\N
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
\.


--
-- Data for Name: kategorie; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id, icon) FROM stdin;
1	Elektro	\N	\N
2	Steckdose anschließen	1	\N
3	Lampe montieren	1	\N
4	Lichtschalter tauschen	1	\N
5	Herd anschließen	1	\N
6	Kabel verlegen 	1	\N
7	Sicherung Austauschen 	1	\N
8	Deckenventilator montieren	1	\N
9	Klingel installieren 	1	\N
10	Rauchmelder anbringen 	1	\N
11	Steckdosenleiste fest montieren 	1	\N
12	Netzwerkdose installieren	1	\N
13	Haushaltstätigkeiten	\N	\N
14	Wohnung reinigen 	13	\N
15	Fenster putzen	13	\N
16	Wäsche waschen 	13	\N
17	Bügeln	13	\N
18	Einkäufe erledigen 	13	\N
19	Kühlschrank reinigen 	13	\N
20	Backofen reinigen 	13	\N
21	Haushaltshilfe auf Zeit 	13	\N
22	Urlaubsvertretung Haushalt 	13	\N
23	Gartenarbeiten	\N	\N
24	Rasen mähen 	23	\N
25	Hecke schneiden 	23	\N
26	Unkraut entfernen 	23	\N
27	Garten aufräumen 	23	\N
28	Pflanzen gießen	23	\N
29	Laub entfernen 	23	\N
30	Beete anlegen 	23	\N
31	Sträucher schneiden 	23	\N
32	Gartenmöbel aufbauen 	23	\N
33	Bewässerungssystem prüfen 	23	\N
34	Gartenabfälle entsorgen 	23	\N
35	Technische Hilfe 	\N	\N
36	WLAN einrichten 	35	\N
37	Drucker einrichten 	35	\N
38	PC/Laptop einrichten 	35	\N
39	Smartphone Hilfe 	35	\N
40	Software installieren 	35	\N
41	Smart-TV einrichten 	35	\N
42	E-Mail konto einrichten 	35	\N
43	Datensicherung durchführen 	35	\N
44	Vierenschutz installieren 	35	\N
45	Smart-Home Geräte verbinden 	35	\N
46	Software-Update durchführen 	35	\N
\.


--
-- Data for Name: kategorie_i18n; Type: TABLE DATA; Schema: skillspot; Owner: -
--

COPY skillspot.kategorie_i18n (kategorie_i18n_id, kategorie_id, lang, bezeichnung) FROM stdin;
1	1	en	Electrical
2	2	en	Install a socket outlet
3	3	en	Install a lamp
4	4	en	Replace a light switch
5	5	en	Connect an electric cooker
6	6	en	Run electrical cables
7	7	en	Replace a fuse
8	8	en	Install a ceiling fan
9	9	en	Install a doorbell
10	10	en	Install a smoke detector
11	11	en	Mount a power strip
12	12	en	Install a network outlet
13	13	en	Household tasks
14	14	en	Clean an apartment
15	15	en	Clean windows
16	16	en	Do laundry
17	17	en	Iron clothes
18	18	en	Do grocery shopping
19	19	en	Clean a refrigerator
20	20	en	Clean an oven
21	21	en	Temporary household help
22	22	en	Household help during vacation
23	23	en	Gardening
24	24	en	Mow the lawn
25	25	en	Trim a hedge
26	26	en	Remove weeds
27	27	en	Tidy up the garden
28	28	en	Water plants
29	29	en	Remove leaves
30	30	en	Create garden beds
31	31	en	Prune shrubs
32	32	en	Assemble garden furniture
33	33	en	Check an irrigation system
34	34	en	Dispose of garden waste
35	35	en	Technical help
36	36	en	Set up WiFi
37	37	en	Set up a printer
38	38	en	Set up a PC or laptop
39	39	en	Smartphone help
40	40	en	Install software
41	41	en	Set up a smart TV
42	42	en	Set up an email account
43	43	en	Perform a backup
44	44	en	Install antivirus software
45	45	en	Connect smart home devices
46	46	en	Perform a software update
\.


--
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.anbieter_anbieter_id_seq', 13, true);


--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.benutzer_benutzer_id_seq', 65, true);


--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.bewertung_bewertung_id_seq', 9, true);


--
-- Name: buchung_buchung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.buchung_buchung_id_seq', 10, true);


--
-- Name: chat_message_message_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.chat_message_message_id_seq', 21, true);


--
-- Name: chat_thread_thread_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.chat_thread_thread_id_seq', 10, true);


--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.dienstleistung_dienstleistung_id_seq', 14, true);


--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.kategorie_i18n_kategorie_i18n_id_seq', 46, true);


--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: -
--

SELECT pg_catalog.setval('skillspot.kategorie_kategorie_id_seq', 46, true);


--
-- Name: anbieter anbieter_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.anbieter
    ADD CONSTRAINT anbieter_pkey PRIMARY KEY (anbieter_id);


--
-- Name: benutzer benutzer_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.benutzer
    ADD CONSTRAINT benutzer_pkey PRIMARY KEY (benutzer_id);


--
-- Name: bewertung bewertung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT bewertung_pkey PRIMARY KEY (bewertung_id);


--
-- Name: buchung buchung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT buchung_pkey PRIMARY KEY (buchung_id);


--
-- Name: chat_message chat_message_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.chat_message
    ADD CONSTRAINT chat_message_pkey PRIMARY KEY (message_id);


--
-- Name: chat_thread chat_thread_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.chat_thread
    ADD CONSTRAINT chat_thread_pkey PRIMARY KEY (thread_id);


--
-- Name: dienstleistung dienstleistung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT dienstleistung_pkey PRIMARY KEY (dienstleistung_id);


--
-- Name: flyway_schema_history flyway_schema_history_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pkey PRIMARY KEY (installed_rank);


--
-- Name: kategorie_i18n kategorie_i18n_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT kategorie_i18n_pkey PRIMARY KEY (kategorie_i18n_id);


--
-- Name: kategorie kategorie_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT kategorie_pkey PRIMARY KEY (kategorie_id);


--
-- Name: kategorie_i18n uq_kategorie_lang; Type: CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT uq_kategorie_lang UNIQUE (kategorie_id, lang);


--
-- Name: idx_kategorie_i18n_kategorie_lang; Type: INDEX; Schema: skillspot; Owner: -
--

CREATE INDEX idx_kategorie_i18n_kategorie_lang ON skillspot.kategorie_i18n USING btree (kategorie_id, lang);


--
-- Name: ix_chat_message_thread_created; Type: INDEX; Schema: skillspot; Owner: -
--

CREATE INDEX ix_chat_message_thread_created ON skillspot.chat_message USING btree (thread_id, created_at);


--
-- Name: ux_benutzer_auth0_sub; Type: INDEX; Schema: skillspot; Owner: -
--

CREATE UNIQUE INDEX ux_benutzer_auth0_sub ON skillspot.benutzer USING btree (auth0_sub);


--
-- Name: ux_chat_thread_user_sub_dienstleistung; Type: INDEX; Schema: skillspot; Owner: -
--

CREATE UNIQUE INDEX ux_chat_thread_user_sub_dienstleistung ON skillspot.chat_thread USING btree (user_sub, dienstleistung_id);


--
-- Name: anbieter fk_anbieter_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.anbieter
    ADD CONSTRAINT fk_anbieter_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: bewertung fk_bewertung_anbieter; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_anbieter FOREIGN KEY (anbieter_id) REFERENCES skillspot.anbieter(anbieter_id);


--
-- Name: bewertung fk_bewertung_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: bewertung fk_bewertung_buchung; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_buchung FOREIGN KEY (buchung_id) REFERENCES skillspot.buchung(buchung_id);


--
-- Name: bewertung fk_bewertung_dl; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_dl FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


--
-- Name: buchung fk_buchung_benutzer; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT fk_buchung_benutzer FOREIGN KEY (benutzer_id) REFERENCES skillspot.benutzer(benutzer_id);


--
-- Name: buchung fk_buchung_dl; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.buchung
    ADD CONSTRAINT fk_buchung_dl FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


--
-- Name: chat_message fk_chat_message_thread; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.chat_message
    ADD CONSTRAINT fk_chat_message_thread FOREIGN KEY (thread_id) REFERENCES skillspot.chat_thread(thread_id) ON DELETE CASCADE;


--
-- Name: dienstleistung fk_dl_anbieter; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT fk_dl_anbieter FOREIGN KEY (anbieter_id) REFERENCES skillspot.anbieter(anbieter_id);


--
-- Name: dienstleistung fk_dl_kategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT fk_dl_kategorie FOREIGN KEY (kategorie_id) REFERENCES skillspot.kategorie(kategorie_id);


--
-- Name: kategorie_i18n fk_kategorie_i18n_kategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT fk_kategorie_i18n_kategorie FOREIGN KEY (kategorie_id) REFERENCES skillspot.kategorie(kategorie_id) ON DELETE CASCADE;


--
-- Name: kategorie fk_kategorie_oberkategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: -
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT fk_kategorie_oberkategorie FOREIGN KEY (oberkategorie_id) REFERENCES skillspot.kategorie(kategorie_id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

\unrestrict kXjcQvKqodSdsPR4IcmG8dDSqGy0SdBoNHTOnQIhZfGv5iS5bOqqUojbeUQajtr

