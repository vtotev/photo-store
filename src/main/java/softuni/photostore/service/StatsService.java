package softuni.photostore.service;

import softuni.photostore.model.view.StatsView;

public interface StatsService {
  void onRequest();
  StatsView getStats();
}
