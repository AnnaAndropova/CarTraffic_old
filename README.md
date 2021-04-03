
# Движение по автостраде
<img height="250" float="left" src="https://github.com/AnnaAndropova/CarTraffic/blob/master/img.jpg"></img><br>
Рассматривается движение автомобилей на прямом однорядном (однополосном) участке автострады без перекрестков, в течение некоторого времени.  
Автомобили появляются на одном конце дороги и проезжают по ней до другого конца, стараясь по возможности сохранить начальную (заданную при их появлении) скорость. Автомобили могут иметь разную начальную скорость: начальная скорость – случайная величина, изменяющаяся в заданном диапазоне (например, от 50 до 100 км/час).  
Интервалы между появлениями автомобилей на дороге также являются случайными величинами из определенного интервала (например, от 1 до 5 секунд).  
Считается, что минимальное допустимое сближение двух автомобилей составляет одну длину (корпус) автомобиля, в ином случае происходит авария. Когда автомобиль приближается к идущей впереди машине на утроенное допустимое расстояние, он начинает притормаживать по определенному закону, пока его скорость не сравняется со скоростью передней машины.  
Пусть в таком потоке машин организована искусственная кратковременная задержка одного автомобиля: автомобиль сначала резко замедляется, сбрасывая за некоторое время скорость, а затем после некоторой паузы вновь набирает первоначальную скорость. В результате, если следующий автомобиль не успел притормозить, возникает авария. Может возникнуть и так
называемая пробка – область с высокой плотностью автомобилей, включающая чередование притормаживаний и ускорений до прежней скорости. Действительно, если какой-то автомобиль начинает резко замедляться, идущий за ним автомобиль тоже через некоторое время тормозит. После торможения следует замедленное движение автомобиля, но как только дорога перед ним освобождается, автомобиль ускоряется до первоначальной скорости.  
Пробка обычно возникает, если плотность потока автомобилей достаточно велика, и существует некоторое время, медленно двигаясь навстречу потоку автомобилей и постепенно рассеиваясь. Заметим, что в аварию может попасть не тот автомобиль, что был искусственно задержан, а идущие за ним машины.  
В случае аварии должно прой̆ти некоторое фиксированное время, прежде чем движение на этом участке вновь станет возможным, и после вынужденной остановки машины вновь набирают первоначальную скорость.  
