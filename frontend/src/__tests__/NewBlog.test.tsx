import {render, screen} from "@testing-library/react";
import {describe, expect, it} from "vitest";
import {NewBlog} from "../components/NewBlog.tsx";

describe('New Blog Dialog', () => {

    const props = {
        modal: true,
        toggle: () => {},

        onSubmit: (post: { author: string; subject: string; content: string }) => {}
    };

    it('should show heading', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('heading', {name: 'New blog post'})).toBeInTheDocument();
    });

    it('should show author input', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('textbox', {name: 'input-author'})).toBeInTheDocument();
    });

    it('should show subject input', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('textbox', {name: 'input-subject'})).toBeInTheDocument();
    });

    it('should show content input', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('textbox', {name: 'input-content'})).toBeInTheDocument();
    });

    it('should show submit button', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('button', {name: 'Confirm'})).toBeInTheDocument();
    });

    it('should show cancel button', () => {
        render(<NewBlog {...props}/>);
        expect(screen.getByRole('button', {name: 'Cancel'})).toBeInTheDocument();
    })
})