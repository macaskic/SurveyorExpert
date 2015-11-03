echo runnning commands
adb shell "su -c 'chmod 777 /data'"
adb shell "su -c 'chmod 777 /data/data'"
adb shell "su -c 'chmod 777 /data/data/com.exercise.SurveyorExpert'"
adb shell "su -c 'chmod 777 /data/data/com.exercise.SurveyorExpert/databases'"
adb shell "su -c 'chmod 777 /data/data/com.exercise.SurveyorExpert/databases/SEDB.db'"
adb pull /data/data/com.exercise.SurveyorExpert/databases/SEDB.db .
pause
