import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PoissonService, Poisson } from '../../services/poisson.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-list-poissons',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './list-poissons.component.html',
  styleUrls: ['./list-poissons.component.css'],
})
export class ListPoissonsComponent implements OnInit {
  poissons: Poisson[] = [];
  poissonSelectionneDetail: Poisson | null = null;
  poissonSelectionneForm: Poisson | null = null;

  formPoisson!: FormGroup;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  modeAjout: boolean = false;

  constructor(private poissonService: PoissonService, private fb: FormBuilder) {}

  ngOnInit() {
    this.chargerPoissons();
    this.initForm();
  }

  initForm() {
    this.formPoisson = this.fb.group({
      id: [''],
      nom: ['', Validators.required],
      poids: [0, [Validators.required, Validators.min(0.1)]],
      espece: ['', Validators.required],
      prix: [0, [Validators.required, Validators.min(1)]],
      imageUrl: ['']
    });
  }

  chargerPoissons() {
    this.poissonService.getAll().subscribe(data => {
      this.poissons = data;
    });
  }

  ouvrirCarteDetail(poisson: Poisson) {
    this.poissonSelectionneDetail = poisson;
  }

  fermerCarteDetail() {
    this.poissonSelectionneDetail = null;
  }

  ouvrirFormulaire(poisson: Poisson) {
    this.poissonSelectionneForm = poisson;
    this.modeAjout = false;
    this.formPoisson.patchValue(poisson);
    this.imagePreview = 'http://localhost:8081/backend/' + poisson.imageUrl;
    this.selectedFile = null;
  }

  ouvrirFormulairePourAjout() {
    this.poissonSelectionneForm = null;
    this.modeAjout = true;
    this.formPoisson.reset();
    this.imagePreview = null;
    this.selectedFile = null;
  }

  fermerFormulaire() {
    this.poissonSelectionneForm = null;
    this.modeAjout = false;
    this.formPoisson.reset();
    this.imagePreview = null;
    this.selectedFile = null;
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      this.selectedFile = null;
      return;
    }
    this.selectedFile = input.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  enregistrerModification() {
    if (this.formPoisson.invalid) return;
    const formValue = this.formPoisson.value;

    if (this.selectedFile && this.poissonSelectionneForm) {
      this.poissonService.updatePoissonWithImage(this.poissonSelectionneForm.id!, formValue, this.selectedFile)
        .subscribe({
          next: () => {
            this.fermerFormulaire();
            this.chargerPoissons();
          },
          error: (err) => {
            alert('Erreur lors de la modification avec image');
            console.error(err);
          }
        });
    } else {
      this.poissonService.update(formValue)
        .subscribe({
          next: () => {
            this.fermerFormulaire();
            this.chargerPoissons();
          },
          error: (err) => {
            alert('Erreur lors de la modification');
            console.error(err);
          }
        });
    }
  }

  supprimerPoisson(id?: number) {
    if (!id) return;
    if (!confirm('Voulez-vous vraiment supprimer ce poisson ?')) return;

    this.poissonService.delete(id).subscribe({
      next: () => {
        this.chargerPoissons();
        alert('Poisson supprimé avec succès');
      },
      error: (err) => {
        alert('Erreur lors de la suppression');
        console.error(err);
      }
    });
  }

  ajouterPoisson() {
    if (this.formPoisson.invalid || !this.selectedFile) {
      alert("Veuillez remplir le formulaire et sélectionner une image.");
      return;
    }

    const formValue = this.formPoisson.value;

    this.poissonService.addPoissonWithImage(formValue, this.selectedFile)
      .subscribe({
        next: () => {
          this.fermerFormulaire();
          this.chargerPoissons();
        },
        error: (err) => {
          alert("Erreur lors de l'ajout du poisson");
          console.error(err);
        }
      });
  }
}
