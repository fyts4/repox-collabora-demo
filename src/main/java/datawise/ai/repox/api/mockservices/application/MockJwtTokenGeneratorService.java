package datawise.ai.repox.api.mockservices.application;

import datawise.ai.repox.api.mockservices.application.ports.in.TokenGeneratorUseCase;
import org.springframework.stereotype.Service;

@Service
class MockJwtTokenGeneratorService implements TokenGeneratorUseCase {
    @Override
    public String generateToken() {
        return "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtYXN0ZXJ1c2VyIiwiaXNzIjoiaGl2ZXByby1rcm9ub3MtYXBpIiwiaWF0IjoxNjk2NDAzODQ2LCJuYmYiOjE2OTY0MDM4NDYsImV4cCI6MTcwMDA2Mzg0NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJ0ZW5hbnQiOiJNYWVCSHZpY0RLIiwiaXNfbWFzdGVyIjpmYWxzZSwiY3JlYXRlZEF0IjoxNjk2NDAzODQ2MzYwfQ.14_7hN3yeRFk2cCP8aR-TdTiyM4V9ectC-NQ8BXb10HciE66Z2oVLUbPJBaccBhxkd30UWcZPk1Xl4nPBxT2sw";
    }
}
