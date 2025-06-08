@echo off
echo ========================================
echo ME-SEUM Database Setup
echo ========================================
echo.

echo Setting up database schema...
mysql -u root -p < src\resources\database\schema-my.sql

if %ERRORLEVEL% EQU 0 (
    echo ✅ Schema created successfully!
    echo.
    echo Inserting sample data...
    mysql -u root -p < src\resources\database\data.sql
    
    if %ERRORLEVEL% EQU 0 (
        echo ✅ Sample data inserted successfully!
        echo.
        echo 🎉 Database setup completed!
        echo You can now run your Java application.
    ) else (
        echo ❌ Error inserting sample data.
    )
) else (
    echo ❌ Error creating schema.
)

echo.
pause