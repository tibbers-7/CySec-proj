import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlUserDialogComponent } from './control-user-dialog.component';

describe('ControlUserDialogComponent', () => {
  let component: ControlUserDialogComponent;
  let fixture: ComponentFixture<ControlUserDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ControlUserDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControlUserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
