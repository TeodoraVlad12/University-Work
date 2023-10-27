#include "Admin.h"
#include "service.h"
#include<QMessageBox>

using namespace std;

Admin::Admin(Service& s, TrenchValidator& v, QWidget *parent)
	: QMainWindow(parent), service(s), validator(v)
{
	ui.setupUi(this);
	this->populateList();
}

Admin::~Admin()
{}

void Admin::populateList()
{
	this->ui.listWidgetAdmin->clear();

	vector<Trench> trenchs = this->service.getAllServiceVector();

	for (Trench& t : trenchs) {
		this->ui.listWidgetAdmin->addItem(QString::fromStdString(to_string(t.getSize()) + ", " + t.getColour() + ", " + to_string(t.getPrice()) + ", " + to_string(t.getQuantity()) + ", " + t.getPhotograph()));

	}
}

void Admin::deleteAdmin()
{
	try {
		QString colour = this->ui.colourLineEdit->text();
		string colourStr = colour.toStdString();

		this->validator.validateColour(colourStr);

		this->service.deleteService(colourStr);

		this->populateList();

		this->ui.sizeLineEdit->clear();
		this->ui.colourLineEdit->clear();
		this->ui.priceLineEdit->clear();
		this->ui.photoLineEdit->clear();
		this->ui.quantityLineEdit->clear();



	}
	catch (ValidationException& ex) {
		QMessageBox::critical(this, "Validation Error", ex.what());
	}
}

void Admin::updateAdmin()
{
	try {
		QString size = this->ui.sizeLineEdit->text();
		int sizeInt = size.toInt();
		QString colour = this->ui.colourLineEdit->text();
		string colourStr = colour.toStdString();
		QString price = this->ui.priceLineEdit->text();
		int priceInt = price.toInt();
		QString quantity = this->ui.quantityLineEdit->text();
		int quantityInt = quantity.toInt();
		QString photo = this->ui.photoLineEdit->text();
		string photoStr = photo.toStdString();

		this->validator.validateColour(colourStr);
		this->validator.validatePrice(priceInt);
		this->validator.validateQuantity(quantityInt);
		this->validator.validateSize(sizeInt);
		this->validator.validatePhotoLink(photoStr);

		this->service.updateService(sizeInt, colourStr, priceInt, quantityInt, photoStr);
		this->populateList();

		this->ui.sizeLineEdit->clear();
		this->ui.colourLineEdit->clear();
		this->ui.priceLineEdit->clear();
		this->ui.photoLineEdit->clear();
		this->ui.quantityLineEdit->clear();

	}
	catch (ValidationException& ex) {
		QMessageBox::critical(this, "Validation Error", ex.what());
	}
}

void Admin::addAdmin() {
	try {
		QString size = this->ui.sizeLineEdit->text();
		int sizeInt = size.toInt();
		QString colour = this->ui.colourLineEdit->text();
		string colourStr = colour.toStdString();
		QString price = this->ui.priceLineEdit->text();
		int priceInt = price.toInt();
		QString quantity = this->ui.quantityLineEdit->text();
		int quantityInt = quantity.toInt();
		QString photo = this->ui.photoLineEdit->text();
		string photoStr = photo.toStdString();

		this->validator.validateColour(colourStr);
		this->validator.validatePrice(priceInt);
		this->validator.validateQuantity(quantityInt);
		this->validator.validateSize(sizeInt);
		this->validator.validatePhotoLink(photoStr);

		this->service.addService(sizeInt, colourStr, priceInt, quantityInt, photoStr);
		this->populateList();

		this->ui.sizeLineEdit->clear();
		this->ui.colourLineEdit->clear();
		this->ui.priceLineEdit->clear();
		this->ui.photoLineEdit->clear();
		this->ui.quantityLineEdit->clear();

	}
	catch (ValidationException& ex) {
		QMessageBox::critical(this, "Validation Error", ex.what());
	}


}
