-- create nikke table
create table nikke(
    id integer primary key not null,
    name varchar(50) unique not null,
    manufacturer varchar(8) not null,
    class_type varchar(9) not null,
    burst varchar(1) not null,
    weapon varchar(2) not null,
    element varchar(8) not null
);

-- create banner gacha table
create table banner_gacha(
  id integer primary key autoincrement not null,
  date varchar(24) not null,
  pick_up_name varchar(50) not null,
  gems_used integer not null,
  ticket_used integer not null,
  total_attempt integer(4) not null,
  total_ssr integer(2) not null
);

-- create mold gacha table
create table mold_gacha(
    id integer primary key autoincrement not null,
    date varchar(24) not null,
    type integer(1) not null,
    amount integer(2) not null,
    total_ssr integer(2) not null
);

-- create relations table
create table banner_gacha_nikke_pulled(
  id integer primary key autoincrement not null,
  banner_gacha_id integer not null,
  nikke_pulled_id integer not null
);
create table mold_gacha_nikke_pulled(
  id integer primary key autoincrement not null,
  mold_gacha_id integer not null,
  nikke_pulled_id integer not null
);
