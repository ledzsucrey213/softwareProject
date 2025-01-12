DROP DATABASE if exists CarService;
CREATE database CarService;
use CarService;
DROP TABLE IF EXISTS cars_in_garage;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS payment_type;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS transaction;

CREATE TABLE user
(
    ID_user  INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username varchar(50) UNIQUE                    NOT NULL,
    name     VARCHAR(50)                           NOT NULL,
    surname  VARCHAR(50)                           NOT NULL,
    password VARCHAR(255)                          NOT NULL,
    role     ENUM ('ADMIN', 'MANAGER', 'CLIENT') NOT NULL
);

CREATE TABLE brand
(
    id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE vehicle
(
    id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    year        YEAR                                                                            NOT NULL,
    color       VARCHAR(50)                                                                     NOT NULL,
    type        ENUM (
        'Sedan',
        'Hatchback',
        'Coupe',
        'Convertible',
        'SUV',
        'Crossover',
        'Minivan',
        'PickupTruck',
        'BoxTruck',
        'FlatbedTruck',
        'DumpTruck',
        'TowTruck',
        'SemiTruck',
        'CargoVan',
        'PassengerVan',
        'SprinterVan',
        'CityBus',
        'CoachBus',
        'SchoolBus',
        'Bulldozer',
        'Excavator',
        'CementMixerTruck',
        'FireTruck',
        'Ambulance',
        'PoliceCar',
        'Motorhome',
        'CamperVan',
        'FifthWheelTrailer',
        'ATV',
        'UTV',
        'Motorcycle',
        'Scooter',
        'Bicycle',
        'Tractor'
        )                                                                                       NOT NULL,
    fuel_type   ENUM ('DIESEL', 'GASOLINE', 'ELECTRIC', 'HYBRID', 'HYDROGEN', 'ETHANOL', 'GAS') NOT NULL,
    engine_size INT UNSIGNED                                                                    NOT NULL,
    brand_id    INT UNSIGNED,
    model       VARCHAR(50),

    FOREIGN KEY (brand_id) REFERENCES Brand (id) ON DELETE SET NULL
);

CREATE TABLE cars_in_garage
(
    id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    car_id        INT UNSIGNED,
    owner_id      INT UNSIGNED,
    licence_plate varchar(30) UNIQUE,
    FOREIGN KEY (owner_id) REFERENCES user (ID_user) ON DELETE SET NULL,
    FOREIGN KEY (car_id) REFERENCES vehicle (id) ON DELETE SET NULL
);

CREATE TABLE subscription
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type        ENUM ('Monthly', 'Yearly') NOT NULL,
    label       VARCHAR(255)               NOT NULL,
    is_active   BOOLEAN                    NOT NULL,
    amount      DECIMAL(10, 2)             NOT NULL,
    description TEXT
);

CREATE TABLE order
(
    id                  INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    partName            VARCHAR(255)                                        NOT NULL,
    partQuantity        INT                                                 NOT NULL,
    orderStatus         ENUM ('Not ordered', 'In progress', 'Arrived')      NOT NULL,
    price               DECIMAL(10, 2)                                      NOT NULL,
    estimatedArrival    Date                                                NOT NULL,
);

CREATE TABLE payment_type (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  label varchar(50) NOT NULL,
  fees DOUBLE NOT NULL,
  isAvailable BOOLEAN NOT NULL
);

CREATE TABLE item
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    label       varchar(50)  NOT NULL,
    description varchar(255) NOT NULL DEFAULT '',
    price       DOUBLE       NOT NULL
);

CREATE TABLE bill
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    client_id   INT UNSIGNED NOT NULL,
    service_type varchar(50) NOT NULL,
    bill_date    DATE NOT NULL,
    bill_status  varchar(50) NOT NULL,
    cost DOUBLE NOT NULL
);

CREATE TABLE cart
(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    owner_id INT UNSIGNED NOT NULL,

    FOREIGN KEY (owner_id) REFERENCES user (ID_user) ON DELETE CASCADE
);

CREATE TABLE transaction
(
    id      INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    cart_id INT UNSIGNED NOT NULL,
    item_id INT UNSIGNED NOT NULL,
    amount  INT UNSIGNED NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO user (ID_user, username, name, surname, role, password)
VALUES (1, 'john_doe', 'John', 'Doe', 'ADMIN', 'john.doe'),
       (2, 'jane_smith', 'Jane', 'Smith', 'ADMIN', 'jane.smith'),
       (3, 'alice_johnson', 'Alice', 'Johnson', 'ADMIN', 'alice.johnson'),
        (4, 'omaritto', 'Omar', 'EL BAF', 'ADMIN', 'OB');



INSERT INTO subscription (type, label, is_active, amount, description)
VALUES ('Monthly', 'Basic Car Wash', true, 15.99, 'Monthly subscription for a basic car wash service.'),
       ('Yearly', 'Premium Car Wash', true, 149.99, 'Yearly subscription for unlimited premium car washes.'),
       ('Monthly', 'Tire Check & Inflation', true, 9.99, 'Monthly subscription for tire pressure check and inflation.'),
       ('Yearly', 'Oil Change Package', false, 199.99, 'Yearly subscription for up to 3 oil changes.'),
       ('Monthly', 'Car Detailing', true, 49.99, 'Monthly subscription for interior and exterior car detailing.'),
       ('Yearly', 'Roadside Assistance', true, 99.99, 'Yearly subscription for 24/7 roadside assistance.'),
       ('Monthly', 'Battery Check', false, 7.99, 'Monthly subscription for battery health checks.'),
       ('Yearly', 'Full Maintenance Package', true, 499.99,
        'Yearly subscription covering full maintenance services for your car.');


-- Inserting sample data into the `brand` table
INSERT INTO brand (name)
VALUES ('Toyota'),
       ('Ford'),
       ('Chevrolet'),
       ('Honda'),
       ('BMW'),
       ('Mercedes-Benz'),
       ('Audi'),
       ('Tesla'),
       ('Nissan'),
       ('Hyundai'),
       ('Volkswagen'),
       ('Jeep'),
       ('Mazda'),
       ('Subaru'),
       ('Porsche'),
       ('Kia'),
       ('Land Rover'),
       ('Lexus'),
       ('Chrysler');


-- Inserting sample data into the `vehicle` table
INSERT INTO vehicle (year, color, type, fuel_type, engine_size, brand_id, model)
VALUES (2023, 'Red', 'SUV', 'GASOLINE', 2500, 1, 'RAV4'),
       (2022, 'Blue', 'Sedan', 'HYBRID', 2200, 2, 'Fusion'),
       (2021, 'Black', 'PickupTruck', 'DIESEL', 3500, 3, 'Silverado'),
       (2020, 'White', 'Hatchback', 'ELECTRIC', 1500, 4, 'Civic'),
       (2023, 'Gray', 'Crossover', 'HYBRID', 2000, 5, 'X5'),
       (2019, 'Silver', 'Coupe', 'GASOLINE', 3000, 6, 'C-Class'),
       (2021, 'Green', 'Convertible', 'GASOLINE', 3000, 7, 'A4 Cabriolet'),
       (2022, 'Yellow', 'Minivan', 'GAS', 2500, 8, 'Model X'),
       (2023, 'Orange', 'BoxTruck', 'DIESEL', 5000, 9, 'NV3500'),
       (2020, 'Purple', 'FireTruck', 'DIESEL', 8000, 10, 'Actros'),
       (2018, 'Brown', 'TowTruck', 'DIESEL', 4000, 11, 'F350'),
       (2021, 'Red', 'CityBus', 'DIESEL', 6000, 12, 'Transporter'),
       (2017, 'White', 'SchoolBus', 'GASOLINE', 5500, 13, 'Lion Electric'),
       (2022, 'Black', 'Ambulance', 'DIESEL', 4000, 14, 'Sprinter'),
       (2019, 'Blue', 'Excavator', 'DIESEL', 9000, 15, 'Caterpillar'),
       (2021, 'Pink', 'CementMixerTruck', 'DIESEL', 7000, 16, 'Mack'),
       (2020, 'Gold', 'PoliceCar', 'GASOLINE', 2500, 17, 'Crown Victoria'),
       (2021, 'Green', 'Motorhome', 'GASOLINE', 4000, 18, 'Sprinter Van'),
       (2023, 'Red', 'Tractor', 'DIESEL', 5000, 19, 'John Deere');

INSERT INTO item (label, description, price) VALUES
                                                 ('Car Seat Cover', 'Protects your car seats from spills and dirt.', 19.99),
                                                 ('Steering Wheel Cover', 'Adds comfort and protects the steering wheel.', 14.99),
                                                 ('Dashboard Camera', 'Records the road for security and insurance purposes.', 59.99),
                                                 ('Blind Spot Mirrors', 'Helps eliminate blind spots for safer driving.', 7.99),
                                                 ('GPS Navigation System', 'Helps you find your way with turn-by-turn navigation.', 99.99),
                                                 ('Car Phone Mount', 'Conveniently holds your phone while driving.', 9.99),
                                                 ('Sunshade for Windshield', 'Blocks UV rays and keeps the car cooler.', 12.99),
                                                 ('Car Organizer', 'Keeps your car interior neat and tidy.', 15.99),
                                                 ('Bluetooth Car Adapter', 'Adds Bluetooth functionality to your car stereo.', 19.99),
                                                 ('LED Headlights', 'Upgrade your car’s lighting to brighter LED headlights.', 49.99),
                                                 ('Floor Mats', 'Protects your car floor from dirt and moisture.', 24.99),
                                                 ('Car Air Purifier', 'Improves air quality inside your car by filtering out dust and allergens.', 39.99),
                                                 ('Portable Jump Starter', 'Helps jump-start your car when the battery is dead.', 69.99),
                                                 ('Trunk Organizer', 'Keeps your trunk organized and tidy.', 18.99),
                                                 ('Car Cleaning Kit', 'Includes tools to clean every corner of your car.', 22.99),
                                                 ('Tire Pressure Gauge', 'Measures your tire pressure to ensure optimal safety.', 5.99),
                                                 ('Windshield Wiper Blades', 'Replaces old and worn windshield wipers.', 14.99),
                                                 ('Car Vacuum Cleaner', 'Helps keep your car clean by removing dirt and debris.', 29.99),
                                                 ('Backseat Car Mirror', 'Provides a view of your rear-facing child.', 11.99),
                                                 ('Car Battery Charger', 'Charges your car’s battery when needed.', 49.99),
                                                 ('Car Cover', 'Protects your car from the elements when parked outside.', 39.99),
                                                 ('Parking Sensor System', 'Alerts you when objects are near your car during parking.', 79.99),
                                                 ('Fog Lights', 'Improves visibility in foggy weather conditions.', 34.99),
                                                 ('Roof Rack', 'Provides extra storage space on top of your car.', 99.99),
                                                 ('Cargo Net', 'Helps secure items in the trunk or cargo area.', 14.99),
                                                 ('Key Finder', 'Helps you locate your car keys with the press of a button.', 19.99),
                                                 ('Car Seat Protector', 'Protects your car’s upholstery from child car seats.', 12.99),
                                                 ('Portable Car Heater', 'Provides warmth in cold weather for your car’s interior.', 27.99),
                                                 ('Windshield Repair Kit', 'Allows you to repair small cracks in your windshield.', 16.99),
                                                 ('Oil Change Kit', 'Provides tools and oil for changing your car’s oil at home.', 34.99),
                                                 ('Car Wax', 'Adds a protective layer and shine to your car’s paint.', 24.99),
                                                 ('Car Jump Leads', 'Provides jump-start capability for other vehicles.', 12.99),
                                                 ('Luggage Carrier', 'Conveniently stores extra luggage on your car’s roof.', 89.99),
                                                 ('Seat Belt Cutter & Window Breaker', 'Emergency tool for escaping a trapped car.', 11.99),
                                                 ('LED Strip Lights', 'Adds stylish LED lights to the interior of your car.', 29.99),
                                                 ('Tire Repair Kit', 'Helps fix flat tires with patches and a pump.', 19.99),
                                                 ('Sunglasses Holder', 'Conveniently stores your sunglasses in the car.', 7.99),
                                                 ('Car Cup Holder Expander', 'Adds extra cup holders to your car for convenience.', 9.99),
                                                 ('Magnetic Phone Holder', 'Mounts your phone to the dashboard with a magnetic holder.', 6.99),
                                                 ('License Plate Frame', 'Customizable frame to enhance the appearance of your license plate.', 10.99),
                                                 ('Portable Car Fridge', 'Keeps drinks and snacks cold during long trips.', 79.99),
                                                 ('Car Air Freshener', 'Keeps your car smelling fresh and pleasant.', 4.99),
                                                 ('Headrest Hooks', 'Hangs your bags or clothes on the headrest hooks.', 8.99),
                                                 ('All-Weather Floor Mats', 'Heavy-duty floor mats for year-round protection.', 39.99);


INSERT INTO payment_type (label, fees, isAvailable)
VALUES
    ('Credit Card', 2.5, TRUE),
    ('Debit Card', 1.5, TRUE),
    ('PayPal', 3.0, TRUE),
    ('Apple Pay', 2.0, TRUE),
    ('Google Pay', 2.0, TRUE),
    ('Bank Transfer', 0.5, TRUE),
    ('Cash', 0.0, TRUE),
    ('Cryptocurrency', 1.8, FALSE),
    ('Check', 0.7, TRUE),
    ('Gift Card', 0.0, TRUE),
    ('Stripe', 2.9, TRUE),
    ('Venmo', 1.2, TRUE);
