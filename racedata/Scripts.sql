--Datos de los managers
select * from manager

--Carreras guardadas por temporada de un manager
select
	count(1),
	rd.id_season,
	m.code_manager
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
where
	m.code_manager = 'GEOFF'
group by
	rd.id_season,
	m.code_manager
order by
	rd.id_season,
	m.code_manager

	
--Carreras guardadas
select count(1) from racedata


--Carreras guardadas de cada manager
select
	count(1),
	m.code_manager
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
group by
	m.code_manager
order by
	m.code_manager


--Datos de una carrera concreta
select
	(select count(1) from pitstop where id_racedata = r.id_racedata) pits,
	(select count(1) from techproblem where id_racedata = r.id_racedata) techs,
	r.*
from
	racedata r
inner join manager m on
	r.id_manager = m.id_manager
inner join track t on
	t.id_track = r.id_track
where
	m.code_manager = 'MIKKO'
	and r.id_season = 42
	and t.name_track = 'Poznan'
	

--Borrar una carrera (techs)
delete from techproblem where id_racedata in (
select
	rd.id_racedata
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
where
	m.code_manager = 'MIKKO'
	and rd.id_season in (39, 38, 37, 36, 35))
	
--Borrar una carrera (pits)	
delete from pitstop where id_racedata in (
select
	rd.id_racedata
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
where
	m.code_manager = 'MIKKO'
	and rd.id_season in (39, 38, 37, 36, 35))	
	
--Borrar una carrera (laps)
delete from racelapdata where id_racedata in (
select
	rd.id_racedata
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
where
	m.code_manager = 'MIKKO'
	and rd.id_season in (39, 38, 37, 36, 35))	

--Borrar una carrera (data)	
delete from racedata where id_racedata in (
select
	rd.id_racedata
from
	racedata rd
	inner join manager m on rd.id_manager =  m.id_manager
where
	m.code_manager = 'MIKKO'
	and rd.id_season in (39, 38, 37, 36, 35))	
	
	
	
select * from season

select * from race	

select * from applicant

delete from applicant

select * from category

select nextval('applicant_pro_id_applicant_pro_seq')

select 

CREATE SEQUENCE gprodata.applicant_pro_id_applicant_pro_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 8
	
	
	
	
	

CREATE TABLE gprodata.recruitment (
	id_recruitment bigserial NOT NULL,
	recruitment_code varchar(6) not null unique,
	recruitment_desc varchar(50) not null,
	id_season integer not null,
	recruitment_start date not null,
	recruitment_end date null
	)
WITH (
	OIDS=FALSE
) ;	

ALTER TABLE gprodata.recruitment ADD CONSTRAINT recruitment_pkey PRIMARY KEY (id_recruitment);


ALTER TABLE gprodata.applicant ADD CONSTRAINT applicant_recruitment_fk FOREIGN KEY (id_recruit_process) REFERENCES recruitment(id_recruitment);


INSERT INTO gprodata.recruitment
(recruitment_code, recruitment_desc, id_season, recruitment_start, recruitment_end)
VALUES('S66RE', 'Recruitment at S66 after Chris leaving', 66, '08/08/2018', NULL);

select * from recruitment



recruit_question (id_recruit_question, recruit_question_text, creation_date)

used_recruit_question (id_recruitment, id_recruit_question, question_order)

recruit_question_answer id_recruitment, id_recruit_question, id_applicant, answer_text, answer_date