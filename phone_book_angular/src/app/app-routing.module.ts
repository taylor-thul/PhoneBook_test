import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecordListComponent } from './components/record-list/record-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'records', pathMatch: 'full'},
  { path: 'records', component: RecordListComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
