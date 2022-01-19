package bg.tu_varna.sit.group17.controllers;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.database.users.Consumer;

public interface InitializeData {
	void initData(Load load, Consumer consumer);
}
