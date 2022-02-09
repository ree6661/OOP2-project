package bg.tu_varna.sit.group17.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.controllers.FirmaController;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.property.Office;
import bg.tu_varna.sit.group17.database.users.Courier;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public final class Firma {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	
	private Load load;
	private Company company;
	private Office office;
	private Courier courier;

	private ComboBox<String> companies, offices, city, couriers;
	private TextField firmaName, address, courierTextField, phone, password, plik, kolet, paket, tovar;
	
	public Firma(FirmaController fc) {
		this.load = fc.load;
		this.company = new Company();
		this.office = new Office();
		this.courier = new Courier();

		this.companies = fc.companies;
		this.offices = fc.offices;
		this.city = fc.city;
		this.couriers = fc.couriers;
		this.firmaName = fc.firmaName;
		this.address = fc.address;
		this.courierTextField = fc.courierTextField;
		this.phone = fc.phone;
		this.password = fc.password;
		this.plik = fc.plik;
		this.kolet = fc.kolet;
		this.paket = fc.paket;
		this.tovar = fc.tovar;
	}

	public void prepareForm() {
		if (companies.getItems().size() == 0) {
			companies.getItems().addAll(load.getProperty().getCities().keySet());
			companies.valueProperty().addListener(firmaListener());
		}
		if (this.city.getItems().size() == 0) {
			this.city.getItems().addAll(load.getProperty().getCities().keySet());
			this.city.getSelectionModel().select(0);
		}
		offices.valueProperty().addListener(officesListener());
		couriers.valueProperty().addListener(couriersListener());
	}

	public void addFirma() {
		try {
			String name = firmaName.getText();
			if (name.equals("")) {
				throw new IllegalArgumentException("Полето име на фирма е празно");
			}

			if (TableQuery.getCompanyId(name) > 0) {
				throw new IllegalArgumentException("Не може да се добави фирмата. Вече съществува фирма с такова име");
			}

			double plik = -1, kolet = -1, paket = -1, tovar = -1;

			plik = Double.parseDouble(this.plik.getText());
			kolet = Double.parseDouble(this.kolet.getText());
			paket = Double.parseDouble(this.paket.getText());
			tovar = Double.parseDouble(this.tovar.getText());
			if (!Valid.price(plik) || !Valid.price(kolet) || !Valid.price(paket) || !Valid.price(tovar))
				throw new IllegalArgumentException("Невалидна цена");

			Add.companyPrice(name, plik, kolet, paket, tovar);
			message.alert("Успешно добавена фирма");
			logger.info("Successfully added company");
			companies.getItems().clear();
			load.getProperty().initCompanies();
			companies.getItems().addAll(load.getProperty().getCompanies().keySet());

		} catch (NumberFormatException | NullPointerException | SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void changeFirma() {
		try {
			String name = firmaName.getText();// companies.getSelectionModel().getSelectedItem();
			if (name.equals("") || name.length() < 3) {
				throw new IllegalArgumentException("Невалидно име на фирма");
			}
			int id_company;

			id_company = TableQuery.getCompanyId(companies.getSelectionModel().getSelectedItem());
			if (id_company < 1) {
				throw new IllegalArgumentException("Фирмата не може да се промени, защото не съществува");
			}

			double plik = -1, kolet = -1, paket = -1, tovar = -1;

			plik = Double.parseDouble(this.plik.getText());
			kolet = Double.parseDouble(this.kolet.getText());
			paket = Double.parseDouble(this.paket.getText());
			tovar = Double.parseDouble(this.tovar.getText());
			if (!Valid.price(plik) || !Valid.price(kolet) || !Valid.price(paket) || !Valid.price(tovar))
				throw new IllegalArgumentException("Невалидна цена");

			Update.companyCategory(id_company, name, plik, kolet, paket, tovar);
			message.alert("Фирмата е успешно редактирана");
			logger.info("Successful changed company");
			companies.getItems().clear();
			load.getProperty().initCompanies();
			companies.getItems().addAll(load.getProperty().getCompanies().keySet());
			companies.getSelectionModel().selectFirst();

		} catch (NumberFormatException | NullPointerException | SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void deleteFirma() {
		try {
			if (this.company == null) {
				message.alert("Не е избрана фирма");
				return;
			}
			Delete.companyCategory(this.company);
			this.company = null;

			message.alert("Фирмата е успешно изтрита");
			logger.info("Successful deleted company");

			if (couriers.getItems() == null)
				return;
			couriers.getItems().clear();

			if (offices.getItems() == null)
				return;
			offices.getItems().clear();

			if (companies.getItems() == null)
				return;
			companies.getItems().clear();

			load.getProperty().initCompanies();
			companies.getItems().addAll(load.getProperty().getCompanies().keySet());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void addOffice() {
		try {
			if (company == null) {
				throw new IllegalArgumentException("Фирмата е празна");
			}
			String cityName = this.city.getSelectionModel().getSelectedItem(), address = this.address.getText();

			if (cityName == null || address == null || cityName.isBlank() || address.isBlank()) {
				throw new IllegalArgumentException("Не може града и/или адреса да са празни");
			}

			int cityIndex = load.getProperty().getCities().get(cityName);
			String sql = "select * from office where id_company='" + this.company.getId() + "' and id_city='"
					+ cityIndex + "'and address='" + address + "'";
			ResultSet rs = TableQuery.execute(sql);
			if (rs != null) {
				throw new IllegalArgumentException("Вече съществува такъв офис.");
			}
			Add.office(this.company.getId(), cityIndex, address);

			message.alert("Успешно добавен офис");
			logger.info("Successful added office");
			companies.getSelectionModel().select(companies.getSelectionModel().getSelectedIndex());
			offices.getSelectionModel().selectLast();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void changeOffice() {
		try {
			if (this.office == null || this.offices.getPromptText().isBlank()
					|| this.companies.getPromptText().isBlank()) {
				throw new IllegalArgumentException("Фирмата и офиса не може да са празни");
			}
			String cityName = this.city.getSelectionModel().getSelectedItem();

			if (cityName.isBlank()) {
				throw new IllegalArgumentException("Градът не е избран");
			}
			int cityIndex = load.getProperty().getCities().get(cityName);
			String address = this.address.getText();

			if (cityIndex == 0 || address.equals("")) {
				throw new IllegalArgumentException("Не може града и/или адреса да са празни");
			}

			String sql = "select * from office where id_company='" + this.company.getId() + "' and id_office='"
					+ this.office.getId_office() + "'";
			ResultSet rs = TableQuery.execute(sql);

			if (rs == null) {
				throw new IllegalArgumentException("Офиса не може да се промени. Не съществува такъв");
			}

			Update.office(this.office.getId_office(), cityIndex, address);
			message.alert("Успешно редактиран офис");
			logger.info("Successful changef office");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}

	}

	public void deleteOffice() {
		try {
			if (this.office == null || this.offices.getPromptText().isBlank()
					|| this.companies.getPromptText().isBlank()) {
				throw new IllegalArgumentException("Фирмата и офиса не може да са празни");
			}
			Delete.office(this.office);
			this.office = null;
			message.alert("Успешно изтрит офис");
			logger.info("Successful deleted office");

			if (couriers.getItems() == null)
				return;
			couriers.getItems().clear();

			if (offices.getItems() == null)
				return;
			offices.getItems().clear();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void addCourier() {
		try {
			if (this.company == null || this.office == null || this.office.getId_office() == 0) {
				throw new IllegalArgumentException("Фирмата или офиса са празни");
			}
			String name = this.courierTextField.getText(), phone = this.phone.getText(),
					password = this.password.getText();

			if (name.equals("") || phone.equals("") || password.equals("")) {
				throw new IllegalArgumentException("Полетата не може да са празни");
			}

			String err = Valid.user(name, phone, password, password);
			if (!err.equals("")) {
				throw new IllegalArgumentException(err);
			}

			String sql = "select * from couriers where id_office='" + this.office.getId_office() + "' and name='" + name
					+ "' and phone='" + phone + "' and password='" + password + "'";
			ResultSet rs = TableQuery.execute(sql);
			if (rs != null) {
				throw new IllegalArgumentException("Вече съществува такъв куриер");
			}
			Add.courier(name, phone, password, this.office.getId_office());
			rs = TableQuery.execute("select * from couriers where phone='" + phone + "'");
			this.courier = Courier.create(rs);
			office.couriers.add(this.courier);
			message.alert("Успешно добавен куриер");
			logger.info("Successful added courier");
			couriers.getSelectionModel().selectFirst();
			rs = TableQuery.execute("select * from couriers where phone='" + phone + "'");
			if (rs != null)
				this.courier = Courier.create(rs);
			companies.getSelectionModel().select(companies.getSelectionModel().getSelectedIndex());
			offices.getSelectionModel().selectLast();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void changeCourier() {
		try {
			if (this.company == null || this.office == null || this.courier == null) {
				throw new IllegalArgumentException("Фирмата офиса и куриера не може да са празни");
			}

			String name = this.courierTextField.getText(), phone = this.phone.getText(),
					password = this.password.getText();

			if (name.equals("") || phone.equals("") || password.equals("") || this.courier.getId() == 0) {
				throw new IllegalArgumentException("Полетата не може да са празни");
			}

			String err = Valid.user(name, phone, password, password);
			if (!err.equals("") && !err.equals("Вече съществува такъв телефон в базата данни")) {
				throw new IllegalArgumentException(err);
			}

			Update.courier(this.courier.getId(), name, phone, password);
			message.alert("Успешно редактиран куриер");
			logger.info("Successful changed courier");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public void deleteCourier() {
		try {
			if (this.company == null || this.office == null || this.courier == null
					|| this.offices.getPromptText().isBlank() || this.companies.getPromptText().isBlank()
					|| this.couriers.getPromptText().isBlank()) {

				throw new IllegalArgumentException("Фирмата офиса и куриера не може да са празни");
			}
			Delete.courier(this.courier);
			this.courier = null;
			message.alert("Успешно изтрит куриер");
			logger.info("Successful deleted courier");

			if (couriers.getItems() == null)
				return;
			couriers.getItems().clear();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	private ChangeListener<String> firmaListener() {
		return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logger.info("Changed firma combobox");

				offices.getItems().clear();
				city.setPromptText(city.getSelectionModel().getSelectedItem().toString());
				address.setText("");

				couriers.getItems().clear();
				courierTextField.setText("");
				phone.setText("");
				password.setText("");

				company = new Company();
				if (newValue == null) return;
				company.setId(load.getProperty().getCompanies().get(newValue));

				company.setName(newValue);

				firmaName.setText(company.getName());

				try {
					String sql = "select * from price_list where id_company='" + company.getId() + "'";
					ResultSet rs = TableQuery.execute(sql);
					if (rs == null) {
						throw new IllegalArgumentException("Фирмата няма цени");
					}
					do {
						switch (rs.getInt("id_category")) {
						case 1:
							plik.setText(Double.toString(rs.getDouble("price")));
						case 2:
							kolet.setText(Double.toString(rs.getDouble("price")));
						case 3:
							paket.setText(Double.toString(rs.getDouble("price")));
						case 4:
							tovar.setText(Double.toString(rs.getDouble("price")));
						}
					} while (rs.next());

					sql = "select * from office where id_company='" + company.getId() + "'";

					rs = TableQuery.execute(sql);

					if (rs == null) {
						offices.setPromptText("Няма офис");
						couriers.setPromptText("Няма куриер");
						return;
					}
					company.setOffices(rs);

					if (company.offices.size() == 0) {
						offices.setPromptText("Няма офис");
						couriers.setPromptText("Няма куриер");
						return;
					}

					for (int i = 0; i < company.offices.size(); ++i) {
						Office office = company.offices.get(i);

						offices.getItems()
								.add(TableQuery.cityIdToName(office.getId_city()) + " " + office.getAddress());
					}
					office = company.offices.get(0);

					offices.getSelectionModel().selectFirst();
					city.setPromptText(office.getCity());
					address.setText(office.getAddress());

				} catch (SQLException e) {
					logger.error("Офиса не е намерен " + office);
					message.alert("Офиса не е намерен");
				}  catch(IllegalArgumentException e) {
					message.alert(e.getMessage());
					logger.error(e.getMessage());
				}
			}
		};
	}

	private ChangeListener<String> officesListener() {
		return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				logger.info("Changed office combobox");

				couriers.getItems().clear();
				courierTextField.setText("");
				phone.setText("");
				password.setText("");

				office = new Office();

				try {
					if (company != null)
						for (int i = 0; i < company.offices.size(); ++i) {
							String officeName = TableQuery.cityIdToName(company.offices.get(i).getId_city()) + " "
									+ company.offices.get(i).getAddress();
							if (officeName.equals(newValue)) {
								office = company.offices.get(i);
								break;
							}
						}
					if (office.getId_office() == 0) {
						offices.setPromptText("Няма офис");
						return;
					}
					city.getSelectionModel().select(TableQuery.cityIdToName(office.getId_city()));
					address.setText(office.getAddress());

					String sql = "select * from couriers where id_office='" + office.getId_office() + "'";
					ResultSet rs = TableQuery.execute(sql);

					if (rs == null) {
						couriers.setPromptText("Няма куриери");
						return;
					}

					office.setCouriers(rs);
					if (office.couriers.size() == 0) {
						couriers.setPromptText("Няма куриери");
						return;
					}

					for (int i = 0; i < office.couriers.size(); ++i) {
						Courier courier = office.couriers.get(i);
						couriers.getItems().add(courier.getName());
					}

					courier = office.couriers.get(0);
					couriers.getSelectionModel().selectFirst();

				} catch (SQLException e) {
					e.printStackTrace();
					message.alert("Не са намират офисите");
				}
			}
		};
	}

	private ChangeListener<String> couriersListener() {
		return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logger.info("Changed courier combobox");

				courierTextField.setText("");
				phone.setText("");
				password.setText("");

				int selectedIndex = couriers.getSelectionModel().getSelectedIndex();
				if (selectedIndex < 0 || selectedIndex > office.couriers.size() - 1)
					return;

				courier = office.couriers.get(selectedIndex);

				courierTextField.setText(courier.getName());
				phone.setText(courier.getPhone());
				password.setText(courier.getPassword());
			}
		};
	}
}
