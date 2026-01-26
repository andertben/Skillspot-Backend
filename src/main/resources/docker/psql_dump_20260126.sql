--
-- PostgreSQL database dump
--

\restrict u5NZmil0YGbeeyI3DEJLsq62VHjV9mSumhrcf6Cs7LiVk24fEAdaeeAjRSOHOty

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
-- Name: skillspot; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA skillspot;


ALTER SCHEMA skillspot OWNER TO postgres;

--
-- Name: SCHEMA skillspot; Type: COMMENT; Schema: -; Owner: postgres
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
    nachname character varying(255),
    auth0_sub text NOT NULL,
    test_col text,
    rolle character varying(50),
    display_name character varying(255),
    location_lat numeric(10,6),
    location_lon numeric(10,6),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    address character varying(255),
    CONSTRAINT check_rolle CHECK (((rolle)::text = ANY ((ARRAY['USER'::character varying, 'PROVIDER'::character varying])::text[])))
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
-- Name: chat_message; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.chat_message (
    message_id bigint NOT NULL,
    thread_id bigint NOT NULL,
    sender_sub text NOT NULL,
    text text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    is_read boolean DEFAULT false NOT NULL
);


ALTER TABLE skillspot.chat_message OWNER TO postgres;

--
-- Name: chat_message_message_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.chat_message_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.chat_message_message_id_seq OWNER TO postgres;

--
-- Name: chat_message_message_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.chat_message_message_id_seq OWNED BY skillspot.chat_message.message_id;


--
-- Name: chat_thread; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.chat_thread (
    thread_id bigint NOT NULL,
    dienstleistung_id bigint NOT NULL,
    user_sub text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE skillspot.chat_thread OWNER TO postgres;

--
-- Name: chat_thread_thread_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
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
-- Name: flyway_schema_history; Type: TABLE; Schema: skillspot; Owner: postgres
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


ALTER TABLE skillspot.flyway_schema_history OWNER TO postgres;

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
-- Name: kategorie_i18n; Type: TABLE; Schema: skillspot; Owner: postgres
--

CREATE TABLE skillspot.kategorie_i18n (
    kategorie_i18n_id bigint NOT NULL,
    kategorie_id bigint NOT NULL,
    lang character varying(5) NOT NULL,
    bezeichnung text NOT NULL
);


ALTER TABLE skillspot.kategorie_i18n OWNER TO postgres;

--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE; Schema: skillspot; Owner: postgres
--

CREATE SEQUENCE skillspot.kategorie_i18n_kategorie_i18n_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE skillspot.kategorie_i18n_kategorie_i18n_id_seq OWNER TO postgres;

--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE OWNED BY; Schema: skillspot; Owner: postgres
--

ALTER SEQUENCE skillspot.kategorie_i18n_kategorie_i18n_id_seq OWNED BY skillspot.kategorie_i18n.kategorie_i18n_id;


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
-- Name: chat_message message_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.chat_message ALTER COLUMN message_id SET DEFAULT nextval('skillspot.chat_message_message_id_seq'::regclass);


--
-- Name: dienstleistung dienstleistung_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung ALTER COLUMN dienstleistung_id SET DEFAULT nextval('skillspot.dienstleistung_dienstleistung_id_seq'::regclass);


--
-- Name: kategorie kategorie_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie ALTER COLUMN kategorie_id SET DEFAULT nextval('skillspot.kategorie_kategorie_id_seq'::regclass);


--
-- Name: kategorie_i18n kategorie_i18n_id; Type: DEFAULT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie_i18n ALTER COLUMN kategorie_i18n_id SET DEFAULT nextval('skillspot.kategorie_i18n_kategorie_i18n_id_seq'::regclass);


--
-- Data for Name: anbieter; Type: TABLE DATA; Schema: skillspot; Owner: postgres
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
-- Data for Name: benutzer; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.benutzer (benutzer_id, beschreibung, email, password_hash, vorname, nachname, auth0_sub, test_col, rolle, display_name, location_lat, location_lon, created_at, updated_at, address) FROM stdin;
62	\N	\N	\N	\N	\N	auth0|6974a49af690ad94a2eea2a1	\N	PROVIDER	Markus Müller	52.410511	12.690256	2026-01-24 11:55:03.582954	2026-01-24 11:55:03.582954	\N
63	\N	\N	\N	\N	\N	auth0|6974a5af1f7b77061b874e16	\N	PROVIDER	Gärtnerei Peter GmbH	52.442937	12.528437	2026-01-24 12:07:20.102859	2026-01-24 12:07:20.102859	\N
15	\N	\N	\N	\N	\N	auth0|69661e55f559c5cbaba4fede	\N	PROVIDER	Testnutzer	52.460930	12.544749	2026-01-23 00:06:11.327142	2026-01-24 14:00:58.690259	Hauptstraße 3, 14778 Beetzsee
65	\N	\N	\N	\N	\N	auth0|6975172a873ec3263f2b8aaa	\N	PROVIDER	Schopf GmbH	52.397701	12.537345	2026-01-24 20:03:12.723391	2026-01-24 20:03:12.723391	\N
57	\N	\N	\N	\N	\N	auth0|69736afeb0529772d1b941e7	\N	USER	Testnutzer2	\N	\N	2026-01-23 13:35:22.281436	2026-01-23 13:35:22.281436	\N
58	\N	\N	\N	\N	\N	auth0|697409dcddf44b588fdbb1e0	\N	PROVIDER	Peter Lustig GmbH	52.371581	12.932913	2026-01-24 00:53:31.690771	2026-01-24 11:52:15.225966	\N
1	Elektromeister	petermüller@mail.de	\N	Peter	Müller	seed:1	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
2	Elektrotechniker 	thomas.schneider@mail.de	\N	Thomas	Schneider	seed:2	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
3	Haushaltshilfe	sabine.krueger@mail.de	\N	Sabine 	Krueger 	seed:3	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
4	Haushaltshilfe	anja.lehmann@mail.de	\N	Anja	Lehmann 	seed:4	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
5	Garten- und Landschaftsbauer 	markus.hoffmann@mail.de	\N	Markus 	Hoffmann	seed:5	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
6	Grünpfleger 	jens.becker@mail.de	\N	Jens 	Becker 	seed:6	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
7	IT-Dienstleister 	daniel.weber@mail.de	\N	Daniel 	Weber	seed:7	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
8	Technischer Service 	stefan.richter@mail.de	\N	Stefan 	Richter	seed:8	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
9	Kunde	max.mustermann@mail.de	\N	Max	Mustermann	seed:9	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
10	Kundin	laura.schulz@mail.de	\N	Laura	Schulz	seed:10	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
11	Kunde	tim.wagner@mail.de	\N	Tim	Wagner	seed:11	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
12	Kundin	sarah.koch@mail.de	\N	Sarah	Koch	seed:12	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
13	Kunde	jonas.braun@mail.de	\N	Jonas 	Braun	seed:13	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
14	Kundin	lena.neumann@mail.de	\N	Lena 	Neumann	seed:14	\N	\N	\N	\N	\N	2026-01-22 23:53:53.879314	2026-01-22 23:53:53.879314	\N
\.


--
-- Data for Name: bewertung; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.bewertung (bewertung_id, dienstleistung_id, benutzer_id, text, anbieter_id, bewertung, erstellungsdatum) FROM stdin;
1	1	1	Sehr guter Service	1	5	2025-12-14 13:56:13.747071
2	1	9	Sehr gute arbeit, alles top.	1	5	2025-12-18 13:56:13.747
3	2	10	Lampe perfekt montiert.	1	5	2025-12-19 20:56:13.747
4	4	13	Wohnung sehr gründlich.	3	5	2025-12-19 13:56:13.747
5	6	10	Rasen sauber gemäht.	5	5	2025-12-17 13:56:13.747
6	8	13	WLAN läuft stabil.	7	5	2025-12-16 13:56:13.747
7	2	63	Gute Arbeit, Steckdose gut angeschlossen. 	1	4	2026-01-24 15:52:33.161095
8	2	63	Bisschen dreckig nach der Montage, sonst ganz gut	1	3	2026-01-24 15:58:46.401792
9	2	63	Top Arbeit	1	5	2026-01-24 15:59:02.254051
\.


--
-- Data for Name: chat_message; Type: TABLE DATA; Schema: skillspot; Owner: postgres
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
-- Data for Name: chat_thread; Type: TABLE DATA; Schema: skillspot; Owner: postgres
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
-- Data for Name: dienstleistung; Type: TABLE DATA; Schema: skillspot; Owner: postgres
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
15	2	2	Ich schließe eine zusätzliche Steckdose fachgerecht an	Anschluss einer Steckdose	\N
16	2	2	Austausch oder Neuinstallation einer Wandsteckdose	Steckdose montieren	\N
17	1	3	Montage und Anschluss einer Deckenleuchte	Lampe montieren	\N
18	2	3	Wandlampe anbringen und elektrisch anschließen	Wandlampe montieren	\N
19	1	4	Austausch eines defekten Lichtschalters	Lichtschalter tauschen	\N
20	2	4	Einbau eines Dimmers oder Schalters	Dimmer installieren	\N
21	1	5	Fachgerechter Anschluss eines Elektroherds	Herd anschließen	\N
22	2	5	Anschluss eines Ceranfeldes inkl. Prüfung	Ceranfeld anschließen	\N
23	1	6	Verlegung von Stromkabeln im Wohnraum	Kabel verlegen	\N
24	2	6	Verlegung von Netzwerk- oder Stromkabeln	Kabelinstallation	\N
25	1	7	Austausch einer Sicherung im Sicherungskasten	Sicherung austauschen	\N
26	2	7	Prüfung und Tausch eines FI-Schalters	FI-Schalter prüfen	\N
27	1	8	Montage eines Deckenventilators	Deckenventilator montieren	\N
28	2	8	Elektrischer Anschluss eines Ventilators	Ventilator anschließen	\N
29	1	9	Installation einer Funkklingel	Klingel installieren	\N
30	2	9	Montage einer kabelgebundenen Türklingel	Türklingel montieren	\N
31	1	10	Montage eines Rauchmelders an Wand oder Decke	Rauchmelder anbringen	\N
32	2	10	Austausch defekter Rauchmelder	Rauchmelder tauschen	\N
33	1	11	Feste Montage einer Steckdosenleiste	Steckdosenleiste montieren	\N
34	2	11	Montage einer Steckdosenleiste inkl. Kabelmanagement	Steckdosenleiste befestigen	\N
35	1	12	Installation einer LAN-Netzwerkdose	Netzwerkdose installieren	\N
36	2	12	Auflegen und Testen einer Netzwerkdose	Netzwerkdose anschließen	\N
37	3	14	Gründliche Reinigung aller Wohnräume	Wohnung reinigen	\N
38	4	14	Regelmäßige Unterhaltsreinigung	Wohnungsreinigung	\N
39	3	15	Fensterreinigung innen inkl. Rahmen	Fenster putzen	\N
40	4	15	Fenster innen und außen reinigen	Fensterreinigung komplett	\N
41	3	16	Waschen und Aufhängen der Wäsche	Wäsche waschen	\N
42	4	16	Bettwäsche und Handtücher reinigen	Wäscheservice	\N
43	3	17	Bügeln von Hemden und Blusen	Bügelservice	\N
44	4	17	Bügeln eines kompletten Wäschekorbs	Wäsche bügeln	\N
45	3	18	Einkäufe nach Liste erledigen	Einkäufe erledigen	\N
46	4	18	Besorgungen im Alltag übernehmen	Besorgungsservice	\N
47	3	19	Gründliche Reinigung des Kühlschranks	Kühlschrank reinigen	\N
48	4	19	Hygienische Innenreinigung des Kühlschranks	Kühlschrank sauber machen	\N
49	3	20	Reinigung des Backofens	Backofen reinigen	\N
50	4	20	Intensive Backofenreinigung	Backofen gründlich reinigen	\N
51	3	21	Flexible Haushaltshilfe auf Zeit	Haushaltshilfe	\N
52	4	21	Unterstützung im Haushalt stundenweise	Haushaltshilfe buchen	\N
53	3	22	Betreuung der Wohnung im Urlaub	Urlaubsvertretung Haushalt	\N
54	4	22	Pflanzen gießen und Postservice	Haushaltsvertretung Urlaub	\N
55	5	24	Rasen mähen inkl. Kanten	Rasen mähen	\N
56	6	24	Regelmäßige Rasenpflege	Rasenpflege	\N
57	5	25	Rückschnitt von Hecken	Hecke schneiden	\N
58	6	25	Formschnitt für Hecken	Heckenschnitt	\N
59	5	26	Unkraut aus Beeten entfernen	Unkraut entfernen	\N
60	6	26	Unkraut aus Pflasterfugen entfernen	Unkrautbeseitigung	\N
61	5	27	Garten aufräumen und säubern	Garten aufräumen	\N
62	6	27	Frühjahrs- oder Herbstputz im Garten	Gartenpflege	\N
63	5	28	Pflanzen während Abwesenheit gießen	Pflanzen gießen	\N
64	6	28	Regelmäßiger Gießservice	Gießservice	\N
65	5	29	Laub sammeln und entsorgen	Laub entfernen	\N
66	6	29	Herbstlaub zusammenkehren	Laubservice	\N
67	5	30	Anlage eines Blumenbeets	Beete anlegen	\N
68	6	30	Vorbereitung eines Gemüsebeets	Hochbeet anlegen	\N
69	5	31	Rückschnitt von Sträuchern	Sträucher schneiden	\N
70	6	31	Pflege von Ziersträuchern	Gehölzschnitt	\N
71	5	32	Aufbau von Gartenmöbeln	Gartenmöbel aufbauen	\N
72	6	32	Montage von Pavillons oder Sets	Gartenmöbel montieren	\N
73	5	33	Prüfung von Sprinkleranlagen	Bewässerung prüfen	\N
74	6	33	Wartung von Bewässerungssystemen	Bewässerung warten	\N
75	5	34	Entsorgung von Grünschnitt	Gartenabfälle entsorgen	\N
76	6	34	Abtransport von Gartenabfällen	Grünschnitt entsorgen	\N
77	7	36	Einrichtung von Router und WLAN	WLAN einrichten	\N
78	8	36	Optimierung der WLAN-Reichweite	WLAN optimieren	\N
79	7	37	Installation eines Druckers	Drucker einrichten	\N
80	8	37	WLAN-Drucker konfigurieren	Drucker konfigurieren	\N
81	7	38	PC oder Laptop startklar machen	PC einrichten	\N
82	8	38	Datenübernahme auf neuen Laptop	Laptop einrichten	\N
83	7	39	Hilfe bei Smartphone-Einrichtung	Smartphone Hilfe	\N
84	8	39	Apps und Backup einrichten	Smartphone Support	\N
85	7	40	Installation gängiger Software	Software installieren	\N
86	8	40	Office und Programme einrichten	Software Service	\N
87	7	41	Smart-TV anschließen und einstellen	Smart-TV einrichten	\N
88	8	41	Streaming-Dienste einrichten	Smart-TV Service	\N
89	7	42	E-Mail auf PC einrichten	E-Mail einrichten	\N
90	8	42	E-Mail auf Smartphone einrichten	Mail-Service	\N
91	7	43	Datensicherung einrichten	Backup durchführen	\N
92	8	43	Wiederherstellung testen	Datensicherung prüfen	\N
93	7	44	Virenschutz installieren	Virenschutz installieren	\N
94	8	44	Sicherheitscheck durchführen	PC absichern	\N
95	7	45	Smart-Home Geräte koppeln	Smart-Home einrichten	\N
96	8	45	Szenen und Automationen erstellen	Smart-Home Service	\N
97	7	46	Betriebssystem aktualisieren	Software-Update	\N
98	8	46	Programme aktualisieren	Updates durchführen	\N
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init schema	SQL	V1__init_schema.sql	624385425	postgres	2026-01-25 18:07:31.69366	247	t
2	2	create kategorie i18n	SQL	V2__create_kategorie_i18n.sql	-1018431841	postgres	2026-01-25 18:07:32.187203	17	t
3	3	seed kategorie i18n en	SQL	V3__seed_kategorie_i18n_en.sql	-768847229	postgres	2026-01-25 18:07:32.291245	19	t
4	4	seed kategorie i18n en full	SQL	V4__seed_kategorie_i18n_en_full.sql	379474377	postgres	2026-01-25 18:07:32.3795	19	t
\.


--
-- Data for Name: kategorie; Type: TABLE DATA; Schema: skillspot; Owner: postgres
--

COPY skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id, icon) FROM stdin;
1	Elektro	\N	⚡
2	Steckdose anschließen	1	🔌
3	Lampe montieren	1	💡
4	Lichtschalter tauschen	1	🔀
5	Herd anschließen	1	🍳
6	Kabel verlegen 	1	🧵
7	Sicherung Austauschen 	1	🛡️
8	Deckenventilator montieren	1	🌀
9	Klingel installieren 	1	🔔
10	Rauchmelder anbringen 	1	🚨
11	Steckdosenleiste fest montieren 	1	🔌
12	Netzwerkdose installieren	1	🌐
13	Haushaltstätigkeiten	\N	🏠
14	Wohnung reinigen 	13	🧹
15	Fenster putzen	13	🪟
16	Wäsche waschen 	13	🧺
17	Bügeln	13	👕
18	Einkäufe erledigen 	13	🛒
19	Kühlschrank reinigen 	13	🧊
20	Backofen reinigen 	13	🧽
21	Haushaltshilfe auf Zeit 	13	👤
22	Urlaubsvertretung Haushalt 	13	✈️
23	Gartenarbeiten	\N	🌿
24	Rasen mähen 	23	🌱
25	Hecke schneiden 	23	✂️
26	Unkraut entfernen 	23	🌾
27	Garten aufräumen 	23	🧹
28	Pflanzen gießen	23	💧
29	Laub entfernen 	23	🍂
30	Beete anlegen 	23	🌸
31	Sträucher schneiden 	23	🌳
32	Gartenmöbel aufbauen 	23	🪑
33	Bewässerungssystem prüfen 	23	🚿
34	Gartenabfälle entsorgen 	23	🗑️
35	Technische Hilfe 	\N	🛠️
36	WLAN einrichten 	35	📶
37	Drucker einrichten 	35	🖨️
38	PC/Laptop einrichten 	35	💻
39	Smartphone Hilfe 	35	📱
40	Software installieren 	35	💿
41	Smart-TV einrichten 	35	📺
42	E-Mail konto einrichten 	35	✉️
43	Datensicherung durchführen 	35	💾
44	Vierenschutz installieren 	35	🛡️
45	Smart-Home Geräte verbinden 	35	🏠
46	Software-Update durchführen 	35	🔄
\.


--
-- Data for Name: kategorie_i18n; Type: TABLE DATA; Schema: skillspot; Owner: postgres
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
36	36	en	Set up Wi-Fi
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
-- Name: anbieter_anbieter_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.anbieter_anbieter_id_seq', 13, true);


--
-- Name: benutzer_benutzer_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.benutzer_benutzer_id_seq', 65, true);


--
-- Name: bewertung_bewertung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.bewertung_bewertung_id_seq', 9, true);


--
-- Name: chat_message_message_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.chat_message_message_id_seq', 21, true);


--
-- Name: chat_thread_thread_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.chat_thread_thread_id_seq', 10, true);


--
-- Name: dienstleistung_dienstleistung_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.dienstleistung_dienstleistung_id_seq', 98, true);


--
-- Name: kategorie_i18n_kategorie_i18n_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.kategorie_i18n_kategorie_i18n_id_seq', 138, true);


--
-- Name: kategorie_kategorie_id_seq; Type: SEQUENCE SET; Schema: skillspot; Owner: postgres
--

SELECT pg_catalog.setval('skillspot.kategorie_kategorie_id_seq', 46, true);


--
-- Name: anbieter anbieter_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.anbieter
    ADD CONSTRAINT anbieter_pkey PRIMARY KEY (anbieter_id);


--
-- Name: benutzer benutzer_auth0_sub_key; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.benutzer
    ADD CONSTRAINT benutzer_auth0_sub_key UNIQUE (auth0_sub);


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
-- Name: chat_message chat_message_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.chat_message
    ADD CONSTRAINT chat_message_pkey PRIMARY KEY (message_id);


--
-- Name: chat_thread chat_thread_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.chat_thread
    ADD CONSTRAINT chat_thread_pkey PRIMARY KEY (thread_id);


--
-- Name: dienstleistung dienstleistung_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.dienstleistung
    ADD CONSTRAINT dienstleistung_pkey PRIMARY KEY (dienstleistung_id);


--
-- Name: flyway_schema_history flyway_schema_history_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pkey PRIMARY KEY (installed_rank);


--
-- Name: kategorie_i18n kategorie_i18n_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT kategorie_i18n_pkey PRIMARY KEY (kategorie_i18n_id);


--
-- Name: kategorie kategorie_pkey; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT kategorie_pkey PRIMARY KEY (kategorie_id);


--
-- Name: kategorie_i18n uq_kategorie_lang; Type: CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT uq_kategorie_lang UNIQUE (kategorie_id, lang);


--
-- Name: idx_chat_message_sender_sub; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE INDEX idx_chat_message_sender_sub ON skillspot.chat_message USING btree (sender_sub);


--
-- Name: idx_chat_message_thread_is_read; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE INDEX idx_chat_message_thread_is_read ON skillspot.chat_message USING btree (thread_id, is_read);


--
-- Name: idx_kategorie_i18n_kategorie_lang; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE INDEX idx_kategorie_i18n_kategorie_lang ON skillspot.kategorie_i18n USING btree (kategorie_id, lang);


--
-- Name: idx_kategorie_i18n_lang; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE INDEX idx_kategorie_i18n_lang ON skillspot.kategorie_i18n USING btree (lang);


--
-- Name: ix_chat_message_thread_created; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE INDEX ix_chat_message_thread_created ON skillspot.chat_message USING btree (thread_id, created_at);


--
-- Name: ux_benutzer_auth0_sub; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE UNIQUE INDEX ux_benutzer_auth0_sub ON skillspot.benutzer USING btree (auth0_sub);


--
-- Name: ux_chat_thread_user_sub_dienstleistung; Type: INDEX; Schema: skillspot; Owner: postgres
--

CREATE UNIQUE INDEX ux_chat_thread_user_sub_dienstleistung ON skillspot.chat_thread USING btree (user_sub, dienstleistung_id);


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
-- Name: bewertung fk_bewertung_dl; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.bewertung
    ADD CONSTRAINT fk_bewertung_dl FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


--
-- Name: chat_message fk_chat_message_thread; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.chat_message
    ADD CONSTRAINT fk_chat_message_thread FOREIGN KEY (thread_id) REFERENCES skillspot.chat_thread(thread_id) ON DELETE CASCADE;


--
-- Name: chat_thread fk_chat_thread_dienstleistung_id; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.chat_thread
    ADD CONSTRAINT fk_chat_thread_dienstleistung_id FOREIGN KEY (dienstleistung_id) REFERENCES skillspot.dienstleistung(dienstleistung_id);


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
-- Name: kategorie_i18n fk_kategorie_i18n_kategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie_i18n
    ADD CONSTRAINT fk_kategorie_i18n_kategorie FOREIGN KEY (kategorie_id) REFERENCES skillspot.kategorie(kategorie_id) ON DELETE CASCADE;


--
-- Name: kategorie fk_kategorie_oberkategorie; Type: FK CONSTRAINT; Schema: skillspot; Owner: postgres
--

ALTER TABLE ONLY skillspot.kategorie
    ADD CONSTRAINT fk_kategorie_oberkategorie FOREIGN KEY (oberkategorie_id) REFERENCES skillspot.kategorie(kategorie_id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

\unrestrict u5NZmil0YGbeeyI3DEJLsq62VHjV9mSumhrcf6Cs7LiVk24fEAdaeeAjRSOHOty

