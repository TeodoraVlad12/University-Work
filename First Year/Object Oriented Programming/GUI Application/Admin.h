#pragma once

#include <QMainWindow>
#include "ui_Admin.h"
#include "Service.h"
#include "validator.h"

class Admin : public QMainWindow
{
	Q_OBJECT

public:
	Admin(Service& s, TrenchValidator& v, QWidget *parent = nullptr);
	~Admin();

private:
	Ui::AdminClass ui;
	Service& service;
	TrenchValidator& validator;

	void populateList();

public slots:
	void addAdmin();
	void deleteAdmin();
	void updateAdmin();
};
