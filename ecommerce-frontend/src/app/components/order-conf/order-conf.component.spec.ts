import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderConfComponent } from './order-conf.component';

describe('OrderConfComponent', () => {
  let component: OrderConfComponent;
  let fixture: ComponentFixture<OrderConfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderConfComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderConfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
