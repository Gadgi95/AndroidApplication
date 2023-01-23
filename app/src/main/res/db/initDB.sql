CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)           NOT NULL,
    email      VARCHAR(255)           NOT NULL,
    password   VARCHAR(255)           NOT NULL,
    registered DATETIME DEFAULT NOW() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE tickets
(
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    user_id               INT                    NOT NULL,
    name                  VARCHAR(255)           NOT NULL,
    creationDate          DATETIME DEFAULT NOW() NOT NULL,
    status                VARCHAR(255)           NOT NULL,
    responsibleSupplier   VARCHAR(255),
    deliveryDate          DATE,
    statusChangeDate      DATE,
    isClosed              BOOLEAN,
    closingDate           DATE,
    closedBy              VARCHAR(255),
    hasFactoryMarriage    BOOLEAN,
    marriageDetectionDate DATE,
    marriageDetectedBy    VARCHAR(255),
    marriageDescription   VARCHAR(255),
    marriagePhotoUrl      VARCHAR(255),
    objectName            VARCHAR(255)
);
CREATE UNIQUE INDEX tickets_unique_user_date_idx ON tickets (user_id, creationDate);

CREATE TABLE tickets_archive
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255)           NOT NULL,
    creationDate DATETIME DEFAULT NOW() NOT NULL,
    closingDate  DATE                   NOT NULL,
    closedBy     INT,
    FOREIGN KEY (closedBy) REFERENCES users (id)
);

CREATE TABLE materials
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id       INT,
    name            VARCHAR(255) NOT NULL,
    quantity        INT          NOT NULL,
    characteristics VARCHAR(255) NOT NULL,
    cost            INT,
    FOREIGN KEY (ticket_id) REFERENCES tickets (id) ON DELETE CASCADE
);
CREATE INDEX materials_name_idx ON materials (name);

CREATE TABLE user_roles
(
    user_id INT          NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE tickets_materials
(
    ticket_id   INT NOT NULL,
    material_id INT NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES tickets (id) ON DELETE CASCADE,
    FOREIGN KEY (material_id) REFERENCES materials (id)
);

SELECT *
FROM tickets
ORDER BY objectName;
SELECT *
FROM tickets
ORDER BY user_id;
SELECT *
FROM tickets
ORDER BY creationDate;

SELECT tickets.id, tickets.name, materials.name
FROM tickets
         JOIN tickets_materials ON tickets.id = tickets_materials.ticket_id
         JOIN materials ON tickets_materials.material_id = materials.id
ORDER BY materials.name;