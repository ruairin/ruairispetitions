CREATE TABLE IF NOT EXISTS Petition (
    id SERIAL PRIMARY KEY ,
    title varchar(255) NOT NULL,
    description TEXT,
    date TIMESTAMP NOT NULL,
    signatures TEXT
);

INSERT INTO Petition (title, description, date, signatures) 
VALUES 
('Remove barriers that prevent advanced practice registered nurses from practicing to their full scope', 'Advanced practice registered nurses (APRNs) have provided safe and effective care in the United States for over four decades. When the Affordable Care ACT (ACA) is fully implemented in 2014 over 30 million Americans will gain coverage under the law. APRNs currently have barriers to practice which include requirements for being supervised by or having a collaborative agreement with a physician, inability to admit patients into hospice or home health and restrictions on prescription of controlled drugs.There is currently a shortage of primary care physicians and the restriction to APRN practice limits patients access to care. Advanced practice registered nurses should be allowed to practice to their full scope of education and training.', CURRENT_TIMESTAMP, 'John Joe (johnjoe@gmail.com)'),
('Make Unlocking Cell Phones Legal', 'The Librarian of Congress decided in October 2012 that unlocking of cell phones would be removed from the exceptions to the DMCA. As of January 26, consumers will no longer be able unlock their phones for use on a different network without carrier permission, even after their contract has expired. Consumers will be forced to pay exorbitant roaming fees to make calls while traveling abroad. It reduces consumer choice, and decreases the resale value of devices that consumers have paid for in full. The Librarian noted that carriers are offering more unlocked phones at present, but the great majority of phones sold are still locked. We ask that the White House ask the Librarian of Congress to rescind this decision, and failing that, champion a bill that makes unlocking permanently legal.', CURRENT_TIMESTAMP, 'Jesse James (jesse@gmail.com)'),
('Publicly affirm your support for strong encryption', 'We demand privacy, security, and integrity for our communications and systems. As a public, we should be confident that the services we use haven’t been weakened or compromised by government mandate or pressure. No legislation, executive order, or private agreement with the government should undermine our rights.', CURRENT_TIMESTAMP, 'Joe Bloggs (joejoe@gmail.com)');
