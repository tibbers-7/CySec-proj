import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerToolbarComponent } from './manager-toolbar.component';

describe('ManagerToolbarComponent', () => {
  let component: ManagerToolbarComponent;
  let fixture: ComponentFixture<ManagerToolbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerToolbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
