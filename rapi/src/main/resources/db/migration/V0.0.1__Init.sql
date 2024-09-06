-- create anomaly interception table
create table anomaly_interceptions(
    id bigint primary key not null,
    date varchar(24),
    boss_name varchar(20) not null,
    stage integer not null,
    drop_type varchar(7) not null,
    dropped boolean not null,
    modules integer not null
);

--create special interception table
create table special_interceptions(
  id bigint primary key not null,
  date varchar(24) not null,
  boss_name varchar(20) not null,
  t9equipment integer not null,
  modules integer not null,
  t9manufacturer_equipment integer not null
);

-- create manufacturer equipment table
create table manufacturer_equipments(
    id bigint primary key not null,
    manufacturer varchar(8) not null,
    class_type varchar(9) not null,
    slot_type varchar(6) not null,
    source_id bigint not null,
    source_type integer not null
);

-- create index for source id on manufacturer equipment table
create index if not exists idx_manufacturer_equipments_source_id on manufacturer_equipments(source_id);
