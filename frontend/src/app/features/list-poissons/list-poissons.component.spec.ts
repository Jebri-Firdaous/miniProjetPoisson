import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPoissonsComponent } from './list-poissons.component';

describe('ListPoissonsComponent', () => {
  let component: ListPoissonsComponent;
  let fixture: ComponentFixture<ListPoissonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListPoissonsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListPoissonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
