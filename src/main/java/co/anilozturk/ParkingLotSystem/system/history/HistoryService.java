package co.anilozturk.ParkingLotSystem.system.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository repository;

    public History save(History history){
        return repository.save(history);
    }
}
