#pragma once

#include <QMainWindow>
#include "ui_AdminWindow.h"

class AdminWindow : public QMainWindow
{
	Q_OBJECT

public:
	AdminWindow(QWidget *parent = nullptr);
	~AdminWindow();

private:
	Ui::AdminWindowClass ui;
};
