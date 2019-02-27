--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5

-- Started on 2019-02-02 03:49:27 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 3251
-- Name: DATABASE "ServizioNavetta"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE "ServizioNavetta" IS 'DataBase per progetto di IGNSW & SIW @unical.it';


--
-- TOC entry 1 (class 3079 OID 13253)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 224 (class 1255 OID 17531)
-- Name: nextid(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.nextid(mytable text) RETURNS integer
    LANGUAGE plpgsql
    AS $$declare var integer;
begin
	execute format('select min(s.i) 
				from generate_series(0,(select max(T."ID") from %I as T)+1) s(i) 
				where not exists (select T1."ID" from %I as T1 where T1."ID"=s.i)',mytable,mytable) into var;
	return var;
end;
$$;


ALTER FUNCTION public.nextid(mytable text) OWNER TO postgres;

--
-- TOC entry 211 (class 1255 OID 17475)
-- Name: undo_operation(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.undo_operation() RETURNS trigger
    LANGUAGE plpgsql
    AS $$begin
	if 0 < (select count(*) from "Persona" where "ID"=NEW."ID")
		then
		return null;
	end if;
	return NEW;
end;
$$;


ALTER FUNCTION public.undo_operation() OWNER TO postgres;

--
-- TOC entry 210 (class 1255 OID 17485)
-- Name: undo_operation_stud(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.undo_operation_stud() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin 
	if 0< (select count(*) from "Persona" where "ID"=NEW."Matricola")
	then
	return null;
	end if;
	return NEW;
end;
$$;


ALTER FUNCTION public.undo_operation_stud() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 17197)
-- Name: Amministratore; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Amministratore" (
    "ID" integer NOT NULL,
    "Nome" "char"[] NOT NULL,
    "Cognome" "char"[] NOT NULL,
    "Password" "char"[] NOT NULL,
    "Email" "char"[] NOT NULL
);


ALTER TABLE public."Amministratore" OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17213)
-- Name: Autista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Autista" (
    "ID" integer NOT NULL,
    "Nome" "char"[] NOT NULL,
    "Cognome" "char"[] NOT NULL,
    "Email" "char"[] NOT NULL,
    "Password" "char"[] NOT NULL
);


ALTER TABLE public."Autista" OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17389)
-- Name: Domanda_Riabilitazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Domanda_Riabilitazione" (
    "ID" integer NOT NULL,
    "Data" date NOT NULL,
    "Ora" time with time zone NOT NULL,
    "Studente_ID" integer NOT NULL,
    "Amministatore_ID" integer NOT NULL
);


ALTER TABLE public."Domanda_Riabilitazione" OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 17564)
-- Name: Feedback; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Feedback" (
    "ID" integer NOT NULL,
    "Contenuto" text NOT NULL
);


ALTER TABLE public."Feedback" OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17237)
-- Name: Fermata; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Fermata" (
    "Nome" "char"[] NOT NULL,
    "Latitudine" double precision NOT NULL,
    "Longitudine" double precision NOT NULL
);


ALTER TABLE public."Fermata" OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 17509)
-- Name: IndexableTables; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."IndexableTables" (
    "TableName" text NOT NULL
);


ALTER TABLE public."IndexableTables" OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 17229)
-- Name: Linea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Linea" (
    "Nome" "char"[] NOT NULL
);


ALTER TABLE public."Linea" OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 17368)
-- Name: Linea_X_Tratto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Linea_X_Tratto" (
    fermata_partenza "char"[] NOT NULL,
    fermata_arrivo "char"[] NOT NULL,
    linea_id "char"[] NOT NULL
);


ALTER TABLE public."Linea_X_Tratto" OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 17245)
-- Name: Navetta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Navetta" (
    "ID" integer NOT NULL,
    "Descrizione" text NOT NULL,
    "Posti_totali" integer NOT NULL
);


ALTER TABLE public."Navetta" OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 17205)
-- Name: Studente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Studente" (
    "Matricola" integer NOT NULL,
    "Flag_attuali" integer NOT NULL,
    "Nome" "char"[] NOT NULL,
    "Cognome" "char"[] NOT NULL,
    "Email" "char"[] NOT NULL,
    "Password" "char"[] NOT NULL
);


ALTER TABLE public."Studente" OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 17453)
-- Name: Persona; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public."Persona" AS
 SELECT a."ID",
    a."Nome",
    a."Cognome",
    a."Email",
    a."Password"
   FROM public."Amministratore" a
UNION
 SELECT b."Matricola" AS "ID",
    b."Nome",
    b."Cognome",
    b."Email",
    b."Password"
   FROM public."Studente" b
UNION
 SELECT c."ID",
    c."Nome",
    c."Cognome",
    c."Email",
    c."Password"
   FROM public."Autista" c;


ALTER TABLE public."Persona" OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 17404)
-- Name: Prenotazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Prenotazione" (
    "ID" integer NOT NULL,
    "Giro" integer DEFAULT 0 NOT NULL,
    "Navetta_ID" integer NOT NULL,
    "Obliterato_entrata" boolean DEFAULT false NOT NULL,
    "Obliterato_uscita" boolean DEFAULT false NOT NULL,
    "Tratto::partenza" "char"[] NOT NULL,
    "Tratto::arrivo" "char"[] NOT NULL,
    "Date_time" timestamp with time zone NOT NULL,
    "Autista_ID" integer NOT NULL,
    "Studente_ID" integer NOT NULL
);


ALTER TABLE public."Prenotazione" OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 17539)
-- Name: TablesIdProvider; Type: MATERIALIZED VIEW; Schema: public; Owner: postgres
--

CREATE MATERIALIZED VIEW public."TablesIdProvider" AS
 SELECT t."TableName",
    public.nextid(t."TableName") AS nextid
   FROM public."IndexableTables" t
  WITH NO DATA;


ALTER TABLE public."TablesIdProvider" OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17253)
-- Name: Tratto_di_linea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Tratto_di_linea" (
    "Fermata_Arrivo" "char"[] NOT NULL,
    "Fermata_Partenza" "char"[] NOT NULL,
    "Tempo_medio_percorrenza_MIN" double precision NOT NULL,
    "Distanza_KM" double precision NOT NULL,
    CONSTRAINT diverso_partenza_arrivo CHECK (("Fermata_Partenza" <> "Fermata_Arrivo"))
);


ALTER TABLE public."Tratto_di_linea" OWNER TO postgres;

--
-- TOC entry 3233 (class 0 OID 17197)
-- Dependencies: 196
-- Data for Name: Amministratore; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Amministratore" ("ID", "Nome", "Cognome", "Password", "Email") FROM stdin;
1	{n}	{c}	{p}	{p,r,o,v,a,@,m,a,i,l,.,s,i}
3	{A,d,m,i,n}	{B,o,s,s}	{b,o,s,s,p,a,s,s}	{s,o,m,e,@,d,o,.,m}
123457	{A,d,m,i,n}	{B,o,s,s}	{b,o,s,s,p,a,s,s}	{s,o,m,e,@,d,o,.,m}
\.


--
-- TOC entry 3235 (class 0 OID 17213)
-- Dependencies: 198
-- Data for Name: Autista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Autista" ("ID", "Nome", "Cognome", "Email", "Password") FROM stdin;
7	{n}	{c}	{e}	{p}
2	{a,l,e,s,s,i,o}	{p,o}	{r,t,@,a,.,r}	{a,r,o}
0	{f}	{i}	{r,@,s,.,t}	{f,i,r,s,t}
\.


--
-- TOC entry 3241 (class 0 OID 17389)
-- Dependencies: 204
-- Data for Name: Domanda_Riabilitazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Domanda_Riabilitazione" ("ID", "Data", "Ora", "Studente_ID", "Amministatore_ID") FROM stdin;
\.


--
-- TOC entry 3245 (class 0 OID 17564)
-- Dependencies: 209
-- Data for Name: Feedback; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Feedback" ("ID", "Contenuto") FROM stdin;
\.


--
-- TOC entry 3237 (class 0 OID 17237)
-- Dependencies: 200
-- Data for Name: Fermata; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Fermata" ("Nome", "Latitudine", "Longitudine") FROM stdin;
{q,u,a,t,t,r,o,m,i,g,l,i,a}	1	1
{u,n,i,v,e,r,s,i,t,a}	2	2
{D,i,m,e,s,_,C,u,b,o,_,4,2}	39.3661269999999988	16.2255280000000006
{U,n,i,v,e,r,s,i,t,y,_,C,l,u,b,_,C,u,b,o,_,2,4}	39.3618030000000019	16.2261409999999984
{D,e,s,f,_,C,u,b,o,_,2}	39.3568240000000031	16.2268459999999983
{P,e,n,s,i,l,i,n,e}	39.3558430000000001	16.2274940000000001
{P,o,l,i,f,u,n,z,i,o,n,a,l,e}	39.3547909999999987	16.2310359999999996
{B,a,k,e,r,_,C,a,n,n,a,t,a,r,o}	39.3538479999999993	16.2314459999999983
{S,v,i,n,c,o,l,o,_,A,3}	39.3532439999999966	16.2350230000000018
{Q,u,a,t,t,r,o,m,i,g,l,i,a}	39.3534009999999981	16.2373170000000009
{P,a,r,c,o,_,G,i,o,r,c,e,l,l,i}	39.3550690000000003	16.2419539999999998
{C,a,s,t,i,g,l,i,o,n,e,_,C,s,.,_,S,t,a,z,i,o,n,e,_,F,S}	39.3555009999999967	16.2436790000000002
{C,o,m,p,l,e,s,s,o,_,G,i,r,a,s,o,l,i}	39.3559649999999976	16.2395549999999993
{B,o,r,r,o,m,e,o}	39.3471979999999988	16.2425050000000013
{D,e,m,a,c,s,_,C,u,b,o,_,3,2}	39.3635939999999991	16.225912000000001
{A,u,l,a,_,M,a,g,n,a,_,B,.,A,n,d,r,e,a,t,t,a,_,C,u,b,o,_,7}	39.3581410000000034	16.2266909999999989
\.


--
-- TOC entry 3243 (class 0 OID 17509)
-- Dependencies: 207
-- Data for Name: IndexableTables; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."IndexableTables" ("TableName") FROM stdin;
Persona
Domanda_Riabilitazione
Feedback
Navetta
\.


--
-- TOC entry 3236 (class 0 OID 17229)
-- Dependencies: 199
-- Data for Name: Linea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Linea" ("Nome") FROM stdin;
{{l},{i},{n},{e},{a}}
{l,i,n,e,a,1}
{l,i,n,e,a,2}
{l,i,n,e,a,3}
{l,i,n,e,a,4}
{l,i,n,e,a,5}
{l,i,n,e,a,6}
{l,i,n,e,a,7}
{l,i,n,e,a,8}
{l,i,n,e,a,9}
{l,i,n,e,a,A}
{l,i,n,e,a,B}
{l,i,n,e,a,C}
{l,i,n,e,a,D}
{l,i,n,e,a,E}
{l,i,n,e,a,F}
\.


--
-- TOC entry 3240 (class 0 OID 17368)
-- Dependencies: 203
-- Data for Name: Linea_X_Tratto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Linea_X_Tratto" (fermata_partenza, fermata_arrivo, linea_id) FROM stdin;
\.


--
-- TOC entry 3238 (class 0 OID 17245)
-- Dependencies: 201
-- Data for Name: Navetta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Navetta" ("ID", "Descrizione", "Posti_totali") FROM stdin;
1	{d}	3
\.


--
-- TOC entry 3242 (class 0 OID 17404)
-- Dependencies: 205
-- Data for Name: Prenotazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Prenotazione" ("ID", "Giro", "Navetta_ID", "Obliterato_entrata", "Obliterato_uscita", "Tratto::partenza", "Tratto::arrivo", "Date_time", "Autista_ID", "Studente_ID") FROM stdin;
1	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
2	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
3	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
4	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
5	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
6	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
7	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
8	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
9	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
10	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
11	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
12	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
13	1	1	f	f	{q,u,a,t,t,r,o,m,i,g,l,i,a}	{u,n,i,v,e,r,s,i,t,a}	1999-01-08 13:05:06+01	7	123456
\.


--
-- TOC entry 3234 (class 0 OID 17205)
-- Dependencies: 197
-- Data for Name: Studente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Studente" ("Matricola", "Flag_attuali", "Nome", "Cognome", "Email", "Password") FROM stdin;
7891012	0	{A,n,t,o,n,i,o}	{A,n,t,o,n,i,i}	{m,a,i,l,@,m,a,i,l,.,m,a,i,l}	{p,a,s,s,2}
123456	0	{N,o,m,e}	{C,o,g,n,o,m,e}	{m,a,i,l,@,m,a,i,l,.,m,a,i,l}	{p,a,s,s,2}
7891011	0	{R,o,c,c,o}	{C,o}	{f,r,a,n,c,o,@,c,o,.,m,a,i,l}	{p,a,s,s,2}
\.


--
-- TOC entry 3239 (class 0 OID 17253)
-- Dependencies: 202
-- Data for Name: Tratto_di_linea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Tratto_di_linea" ("Fermata_Arrivo", "Fermata_Partenza", "Tempo_medio_percorrenza_MIN", "Distanza_KM") FROM stdin;
{u,n,i,v,e,r,s,i,t,a}	{q,u,a,t,t,r,o,m,i,g,l,i,a}	3	3
\.


--
-- TOC entry 3068 (class 2606 OID 17204)
-- Name: Amministratore Amministratore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Amministratore"
    ADD CONSTRAINT "Amministratore_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3072 (class 2606 OID 17220)
-- Name: Autista Autista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Autista"
    ADD CONSTRAINT "Autista_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3089 (class 2606 OID 17393)
-- Name: Domanda_Riabilitazione Domanda_Riabilitazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Domanda_Riabilitazione"
    ADD CONSTRAINT "Domanda_Riabilitazione_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3096 (class 2606 OID 17571)
-- Name: Feedback Feedback1_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Feedback"
    ADD CONSTRAINT "Feedback1_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3076 (class 2606 OID 17244)
-- Name: Fermata Fermata_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Fermata"
    ADD CONSTRAINT "Fermata_pkey" PRIMARY KEY ("Nome");


--
-- TOC entry 3094 (class 2606 OID 17516)
-- Name: IndexableTables IndexableTables_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."IndexableTables"
    ADD CONSTRAINT "IndexableTables_pkey" PRIMARY KEY ("TableName");


--
-- TOC entry 3074 (class 2606 OID 17236)
-- Name: Linea Linea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Linea"
    ADD CONSTRAINT "Linea_pkey" PRIMARY KEY ("Nome");


--
-- TOC entry 3078 (class 2606 OID 17252)
-- Name: Navetta Navetta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Navetta"
    ADD CONSTRAINT "Navetta_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3091 (class 2606 OID 17411)
-- Name: Prenotazione Prenotazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Prenotazione_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 3070 (class 2606 OID 17212)
-- Name: Studente Studente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Studente"
    ADD CONSTRAINT "Studente_pkey" PRIMARY KEY ("Matricola");


--
-- TOC entry 3080 (class 2606 OID 17260)
-- Name: Tratto_di_linea Tratto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tratto_di_linea"
    ADD CONSTRAINT "Tratto_key" PRIMARY KEY ("Fermata_Arrivo", "Fermata_Partenza");


--
-- TOC entry 3082 (class 2606 OID 17288)
-- Name: Tratto_di_linea Tratto_unique_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tratto_di_linea"
    ADD CONSTRAINT "Tratto_unique_key" UNIQUE ("Fermata_Arrivo", "Fermata_Partenza");


--
-- TOC entry 3085 (class 2606 OID 17375)
-- Name: Linea_X_Tratto linea_tratto_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Linea_X_Tratto"
    ADD CONSTRAINT linea_tratto_key PRIMARY KEY (fermata_partenza, fermata_arrivo, linea_id);


--
-- TOC entry 3087 (class 2606 OID 17377)
-- Name: Linea_X_Tratto linea_tratto_unique_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Linea_X_Tratto"
    ADD CONSTRAINT linea_tratto_unique_key UNIQUE (fermata_partenza, fermata_arrivo, linea_id);


--
-- TOC entry 3092 (class 1259 OID 17435)
-- Name: fki_foreign_studente; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_foreign_studente ON public."Prenotazione" USING btree ("Studente_ID");


--
-- TOC entry 3083 (class 1259 OID 17388)
-- Name: fki_foreign_tratto; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_foreign_tratto ON public."Linea_X_Tratto" USING btree (fermata_partenza, fermata_arrivo);


--
-- TOC entry 3108 (class 2620 OID 17486)
-- Name: Studente unique_id_persona; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER unique_id_persona BEFORE INSERT OR UPDATE OF "Matricola" ON public."Studente" FOR EACH ROW EXECUTE PROCEDURE public.undo_operation_stud();


--
-- TOC entry 3107 (class 2620 OID 17482)
-- Name: Amministratore unique_persona_id; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER unique_persona_id BEFORE INSERT OR UPDATE OF "ID" ON public."Amministratore" FOR EACH ROW EXECUTE PROCEDURE public.undo_operation();


--
-- TOC entry 3109 (class 2620 OID 17483)
-- Name: Autista unique_persona_id; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER unique_persona_id BEFORE INSERT OR UPDATE OF "ID" ON public."Autista" FOR EACH ROW EXECUTE PROCEDURE public.undo_operation();


--
-- TOC entry 3102 (class 2606 OID 17399)
-- Name: Domanda_Riabilitazione amministratore_foreign; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Domanda_Riabilitazione"
    ADD CONSTRAINT amministratore_foreign FOREIGN KEY ("Amministatore_ID") REFERENCES public."Amministratore"("ID");


--
-- TOC entry 3098 (class 2606 OID 17334)
-- Name: Tratto_di_linea arrivo_foreign; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tratto_di_linea"
    ADD CONSTRAINT arrivo_foreign FOREIGN KEY ("Fermata_Arrivo") REFERENCES public."Fermata"("Nome");


--
-- TOC entry 3105 (class 2606 OID 17422)
-- Name: Prenotazione foreign_autista; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT foreign_autista FOREIGN KEY ("Autista_ID") REFERENCES public."Autista"("ID");


--
-- TOC entry 3099 (class 2606 OID 17378)
-- Name: Linea_X_Tratto foreign_linea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Linea_X_Tratto"
    ADD CONSTRAINT foreign_linea FOREIGN KEY (linea_id) REFERENCES public."Linea"("Nome");


--
-- TOC entry 3103 (class 2606 OID 17412)
-- Name: Prenotazione foreign_navetta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT foreign_navetta FOREIGN KEY ("Navetta_ID") REFERENCES public."Navetta"("ID");


--
-- TOC entry 3106 (class 2606 OID 17430)
-- Name: Prenotazione foreign_studente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT foreign_studente FOREIGN KEY ("Studente_ID") REFERENCES public."Studente"("Matricola");


--
-- TOC entry 3100 (class 2606 OID 17383)
-- Name: Linea_X_Tratto foreign_tratto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Linea_X_Tratto"
    ADD CONSTRAINT foreign_tratto FOREIGN KEY (fermata_partenza, fermata_arrivo) REFERENCES public."Tratto_di_linea"("Fermata_Partenza", "Fermata_Arrivo") MATCH FULL;


--
-- TOC entry 3104 (class 2606 OID 17417)
-- Name: Prenotazione foreign_tratto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT foreign_tratto FOREIGN KEY ("Tratto::partenza", "Tratto::arrivo") REFERENCES public."Tratto_di_linea"("Fermata_Partenza", "Fermata_Arrivo") MATCH FULL;


--
-- TOC entry 3097 (class 2606 OID 17329)
-- Name: Tratto_di_linea partenza_foreign; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tratto_di_linea"
    ADD CONSTRAINT partenza_foreign FOREIGN KEY ("Fermata_Partenza") REFERENCES public."Fermata"("Nome");


--
-- TOC entry 3101 (class 2606 OID 17394)
-- Name: Domanda_Riabilitazione studente_foreign; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Domanda_Riabilitazione"
    ADD CONSTRAINT studente_foreign FOREIGN KEY ("Studente_ID") REFERENCES public."Studente"("Matricola");


--
-- TOC entry 3244 (class 0 OID 17539)
-- Dependencies: 208 3247
-- Name: TablesIdProvider; Type: MATERIALIZED VIEW DATA; Schema: public; Owner: postgres
--

REFRESH MATERIALIZED VIEW public."TablesIdProvider";


-- Completed on 2019-02-02 03:49:28 CET

--
-- PostgreSQL database dump complete
--

