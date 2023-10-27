#include "A9designer.h"

#include "Admin.h"

A9designer::A9designer(Service& s, TrenchValidator& v, QWidget *parent)
    : QMainWindow(parent) , service(s),validator(v), admin(s,v, this)
{
    ui.setupUi(this);
    //this->admin = new Admin();

    

    //connect(ui.startAdminButton, &QPushButton::clicked, this, &A9designer::openAdminWindow);

}

A9designer::~A9designer()
{}

void A9designer::adminButtonOpen() {
   
    this->admin.show();

}

//void A9designer::openAdminWindow() {
//    //if (!adminWindow)
//    //{
//    //    adminWindow = new AdminWindow(this); // Create an instance of the second window
//    //    //connect(secondWindow, &SecondWindow::windowClosed, this, &MainWindow::onSecondWindowClosed);
//    //    //adminWindow->show(); // Show the second window
//    //}
//}
