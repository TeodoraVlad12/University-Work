#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_A9designer.h"

#include "Admin.h"
#include "service.h"
#include "validator.h"

class A9designer : public QMainWindow
{
    Q_OBJECT

public:
    A9designer(Service& s, TrenchValidator& v, QWidget* parent = nullptr);
    
    ~A9designer();

private:
    Service& service;
    TrenchValidator& validator;

    

    Admin admin;

    Ui::A9designerClass ui;

    //void openAdminWindow();
    //AdminWindow* adminWindow;

public slots:
    void adminButtonOpen();

};
