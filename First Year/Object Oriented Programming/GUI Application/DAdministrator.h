#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_A9designer.h"
#include "ui_AdminWindow.h"
#include "ui_DAdministrator.h"
#include "service.h"

class DAdministrator : public QDialog
{
    Q_OBJECT

public:
    DAdministrator(Service& s, QWidget* parent = nullptr);


private:
    Service& service;
    //Ui::DAdministrator ui;
    
    //AdminWindow* adminWindow;

private slots:
    

};
