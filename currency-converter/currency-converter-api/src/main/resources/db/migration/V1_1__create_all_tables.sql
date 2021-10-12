create table currency_transaction (
      id varchar(255) not null,
      conversion_rate decimal(16,6),
      currency_origin integer,
      date timestamp,
      destination_currency integer,
      source_value decimal(16,6),
      user_id varchar(255),
      primary key (id)
)
