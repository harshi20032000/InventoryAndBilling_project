INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (1, 'LenskartAir', 'RG', 800.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (2, 'LenskartRomeo', 'SG', 200.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (3, 'LenskartTita', 'SG', 300.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (4, 'LenskartBlack', 'SG', 500.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (5, 'LenskartRed', 'SG', 700.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (6, 'LenskartBlue', 'SG', 850.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (7, 'VincentChase', 'RG', 500.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (8, 'MacbookAir', 'LP', 5000.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (9, 'LogiKeyboard', 'EE', 1300.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (10, 'MacbookProM2', 'LP', 137000.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (11, 'AlexaBase', 'EE', 1800.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (12, 'SealedLunchBox', 'AC', 350.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (13, 'AlexaPro', 'EE', 1850.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (14, 'dellInspiron', 'LP', 650000.00);
INSERT INTO fe_user.product ("product_id", "brand_name", "p_type", "base_price") VALUES (15, 'WhiteboardCamel', 'ST', 1300.00);

INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (1, 'HOWRAH', 'HWH');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (2, 'RISHRA', 'RIS');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (3, 'SERAMPORE', 'SER');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (4, 'DANKUNI', 'DAN');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (5, 'RAJARHAT', 'RAJ');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (6, 'SANTRAGACHI', 'STR');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (9, 'DURGAPUR', 'DRG');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (10, 'LILUAH', 'LIL');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (11, 'CHANDANNAGAR', 'CGR');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (12, 'SODEPUR', 'SDP');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (13, 'SANKRAIL', 'SKR');
INSERT INTO fe_user.warehouse ("ware_id", "ware_name", "ware_code") VALUES (14, 'KONNAGAR', 'KGR');

INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (1, 'Patna', 'Amit Mishra');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (2, 'Rajasthan', 'Rahul Jhanjharia');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (3, 'Mumbai', 'Abhijit Saini');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (4, 'Andhra Pradesh', 'Kommula Sai Krishna');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (5, 'Orissa', 'Abhilash Nayak');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (6, 'Hyderabad', 'Ankita');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (7, 'Bhubhaneshwar', 'Vivek Singh');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (8, 'Ahmedabad', 'Sayan Mondal');
INSERT INTO fe_user.reps ("rep_id", "rep_location", "rep_name") VALUES (9, 'Siwan', 'Nisha Shaw');

INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 1, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 2, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 3, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 4, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 5, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 6, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 7, 9);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 8, 10);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 9, 11);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 10, 12);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 12, 13);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (1, 15, 14);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 98, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 97, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 99, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 94, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 95, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 96, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 91, 9);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 92, 10);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 93, 11);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 90, 12);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 9, 13);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (2, 2, 14);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 89, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 88, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 87, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 86, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 85, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 84, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 83, 9);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 82, 10);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 81, 11);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 80, 12);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 8, 13);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (3, 3, 14);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 79, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 78, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 77, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 76, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 75, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 74, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 73, 9);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 72, 10);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 71, 11);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 70, 12);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 7, 13);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (4, 4, 14);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 69, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 68, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 67, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 66, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 65, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 64, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 63, 9);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 62, 10);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 61, 11);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 60, 12);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 6, 13);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (5, 5, 14);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 59, 1);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 58, 2);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 57, 3);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 56, 4);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 55, 5);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 54, 6);
INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES (6, 53, 9);


INSERT INTO fe_user.product_warehouse_quantity (product_id, quantity, warehouse_id) VALUES
(6, 52, 10),
(6, 51, 11),
(6, 50, 12),
(6, 5, 13),
(6, 6, 14),
(7, 49, 1),
(7, 48, 2),
(7, 47, 3),
(7, 46, 4),
(7, 45, 5),
(7, 44, 6),
(7, 43, 9),
(7, 42, 10),
(7, 41, 11),
(7, 40, 12),
(7, 4, 13),
(7, 7, 14),
(8, 39, 1),
(8, 38, 2),
(8, 37, 3),
(8, 36, 4),
(8, 35, 5),
(8, 34, 6),
(8, 33, 9),
(8, 32, 10),
(8, 31, 11),
(8, 30, 12),
(8, 3, 13),
(8, 8, 14),
(9, 50, 1),
(9, 25, 2),
(9, 10, 3),
(9, 23, 4),
(9, 0, 5),
(9, 12, 6),
(9, 11, 9),
(9, 29, 10),
(9, 28, 11),
(9, 27, 12),
(9, 26, 13),
(9, 9, 14),
(10, 100, 1),
(10, 98, 2),
(10, 65, 3),
(10, 25, 4),
(10, 35, 5),
(10, 25, 6),
(10, 87, 9),
(10, 89, 10),
(10, 91, 11),
(10, 92, 12),
(10, 24, 13),
(10, 10, 14),
(11, 100, 1),
(11, 99, 2),
(11, 98, 3),
(11, 97, 4),
(11, 96, 5),
(11, 95, 6),
(11, 92, 9),
(11, 91, 10),
(11, 15, 11),
(11, 14, 12),
(11, 22, 13),
(11, 11, 14);

INSERT INTO fe_user.product_warehouse_quantity ("product_id", "quantity", "warehouse_id") VALUES
(12, 10, 1),
(12, 11, 2),
(12, 12, 3),
(12, 13, 4),
(12, 14, 5),
(12, 15, 6),
(12, 16, 9),
(12, 17, 10),
(12, 18, 11),
(12, 21, 12),
(12, 22, 13),
(12, 12, 14),
(13, 15, 1),
(13, 20, 2),
(13, 13, 3),
(13, 14, 4),
(13, 15, 5),
(13, 65, 6),
(13, 98, 9),
(13, 78, 10),
(13, 33, 11),
(13, 31, 12),
(13, 30, 13),
(13, 13, 14),
(14, 22, 1),
(14, 23, 2),
(14, 25, 3),
(14, 26, 4),
(14, 36, 5),
(14, 12, 6),
(14, 1, 9),
(14, 2, 10),
(14, 1, 11),
(14, 5, 12),
(14, 8, 13),
(14, 14, 14),
(15, 22, 1),
(15, 23, 2),
(15, 21, 3),
(15, 24, 4),
(15, 25, 5),
(15, 26, 6),
(15, 27, 9),
(15, 28, 10),
(15, 29, 11),
(15, 35, 12),
(15, 33, 13),
(15, 34, 14);




