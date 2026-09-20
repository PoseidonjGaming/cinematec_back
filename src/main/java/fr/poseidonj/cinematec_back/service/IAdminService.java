package fr.poseidonj.cinematec_back.service;

import java.util.Map;

public interface IAdminService {
    Map<String, String> getStructure();
    Map<String, String[]> getDisplay();
    Map<String, String> getType();
}
