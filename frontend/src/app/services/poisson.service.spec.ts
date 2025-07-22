import { TestBed } from '@angular/core/testing';

import { PoissonService } from './poisson.service';

describe('PoissonService', () => {
  let service: PoissonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PoissonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
