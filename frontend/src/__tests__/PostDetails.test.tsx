import {PostDetails} from "../pages/PostDetails.tsx";
import {render, screen} from "@testing-library/react";
import {describe, expect, it} from "vitest";
import {userEvent} from "@testing-library/user-event";

describe('Post Details', () => {

    // Should show image
    it('should show image', () => {
        render(<PostDetails />);
        expect(screen.getByRole('img', {name: 'Background'})).toBeInTheDocument();
    });

    // Show button Back
    it('should show back button', () => {
        render(<PostDetails />);
        expect(screen.getByRole('link', {name: 'Back'})).toBeInTheDocument();
    });

    // Show button Edit
    it('should show edit button', () => {
        render(<PostDetails />);
        expect(screen.getByRole('button', {name: 'Edit'})).toBeInTheDocument();
    });

    // Show button Delete
    it('should show delete button', () => {
        render(<PostDetails />);
        expect(screen.getByRole('button', {name: 'Delete'})).toBeInTheDocument();
    });

    // Show button Like
    it('should show like button', () => {
        render(<PostDetails />);
        expect(screen.getByRole('button', {name: 'Like'})).toBeInTheDocument();
    });

    // Should call axios get

    // Should show heading

    it('should not show edit dialog on load', () => {
        render(<PostDetails />);
        expect(screen.queryByRole('dialog')).toBeNull();
    })

    it('should show edit dialog on click', async () => {
        const user = userEvent.setup();
        render(<PostDetails />);
        await user.click(screen.getByRole("button", { name: "Edit"}));
        expect(screen.queryByRole('dialog')).toBeInTheDocument();
    })

    it('should close dialog on cancel'), async () => {
        const user = userEvent.setup();
        render(<PostDetails />);
        await user.click(screen.getByRole('button', {name: 'Edit'}));
        await user.click(screen.getByRole('button', {name: 'Cancel'}));
        expect(screen.queryByRole('dialog')).toBeNull();
    }
});