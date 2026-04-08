create table if not exists TacoOrder (
    id identity primary key,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    creditCardNumber varchar(16) not null,
    creditCardExpiration varchar(5) not null,
    creditCardValidationValue varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists Taco (
    id identity primary key,
    name varchar(50) not null,
    tacoOrder bigint not null,
    tacoOrderKey bigint not null,
    createdAt timestamp not null
);

create table if not exists IngredientRef (
    ingredient varchar(4) not null,
    taco bigint not null,
    tacoKey bigint not null
);

create table if not exists Ingredient (
    id varchar(4) not null primary key,
    name varchar(25) not null,
    kind varchar(10) not null
);

alter table Taco
    add foreign key (tacoOrder) references TacoOrder(id);
alter table IngredientRef
    add foreign key (ingredient) references Ingredient(id);
